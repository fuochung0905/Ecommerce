package com.utc2.it.Ecommerce.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.utc2.it.Ecommerce.entity.Role;
import com.utc2.it.Ecommerce.filters.JwtRequestFilters;
import com.utc2.it.Ecommerce.service.implement.UserDetailServiceImpl;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtRequestFilters requestFilters;
    private final UserDetailServiceImpl userDetailService;
    private final CustomerLogout logoutHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity.csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers("/api/guest/**").permitAll();
                    auth.requestMatchers("/images/**").permitAll();
//                    auth.requestMatchers("/v3/api-docs.yaml").permitAll();
//                    auth.requestMatchers("/v3/api-docs/**").permitAll();
//                    auth.requestMatchers("/swagger-ui/**").permitAll();
//                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.requestMatchers("/api/admin/**").hasAuthority(Role.Admin.name());
                    auth.requestMatchers("/api/user/**").hasAuthority(Role.User.name());
                auth.anyRequest().authenticated();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(requestFilters, UsernamePasswordAuthenticationFilter.class)
                .logout(l->l.logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailService.userDetailsService());
        return authenticationProvider;
    }
 

}
