package com.utc2.it.Ecommerce.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import com.utc2.it.Ecommerce.entity.Token;
import com.utc2.it.Ecommerce.repository.TokenRepository;

@RequiredArgsConstructor
@Component
public class CustomerLogout implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null && !authHeader.startsWith("Bearer ")){
            return;
        }
        String token=authHeader.substring(7);
        Token storedToken=tokenRepository.findByToken( token).orElseThrow(null);
        if(token!=null){
            storedToken.setLogout(true);
            tokenRepository.save(storedToken);
        }
    }
}
