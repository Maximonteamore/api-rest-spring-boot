package com.application.rest.security;

import com.application.rest.security.filter.JwtTokenValidator;
import com.application.rest.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity


public class WebSecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

 /*   @Autowired
    private  UserDetailsService userDetailsService;
*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {


        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( req ->
                req.requestMatchers(   "/api/product/**",
                                "/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/maker/save").hasAnyRole("DEV","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/maker/findAll").hasAnyAuthority("READ")
                        .requestMatchers(HttpMethod.PUT,"/api/maker/update/{id}").hasAnyAuthority("REFACTOR")
                        .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults())
                //.userDetailsService(userDetailsService)
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)//valido token antes del filtro de authenticacion BasicAuthenticationFilter.
                .build();
    }

        @Bean
        public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        }

}
