package com.utc2.it.Ecommerce.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.utc2.it.Ecommerce.service.implement.UserDetailServiceImpl;
import com.utc2.it.Ecommerce.utils.JwtUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilters extends OncePerRequestFilter {
    private final UserDetailServiceImpl userDetailService;
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token= null;
        String username=null;
        if(authHeader!=null && authHeader.startsWith("Bearer")){
            token=authHeader.substring(7);
            username=jwtUtils.extractUsername(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailService.userDetailsService().loadUserByUsername(username);
            if(jwtUtils.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}


