package com.ips.hospitalmanagementsystem.security;

import com.ips.hospitalmanagementsystem.entity.type.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.naming.AuthenticationException;

import static com.ips.hospitalmanagementsystem.entity.type.RoleType.ADMIN;
import static com.ips.hospitalmanagementsystem.entity.type.RoleType.DOCTOR;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    private   final  JwtAuthFilter jwtAuthFilter;
    private  final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**","/auth/**").permitAll()
                         .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .requestMatchers("/doctor/**").hasAnyRole(ADMIN.name(),DOCTOR.name())
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                  .oauth2Login(oauth2 -> oauth2
                        .failureHandler((request,response,exception) -> {

                            log.error("OAuth2 error: {}", exception.getMessage());
                        }
                        ).successHandler(oAuth2SuccessHandler)
                );

        //  .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    }

