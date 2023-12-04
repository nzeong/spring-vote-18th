package com.gotcha.vote.global.config;

import com.gotcha.vote.global.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"/swagger-ui/**", "/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/users/join").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/vote/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "api/users.test").hasAnyRole("USER") 이런 식으로 추가 (회원가입시 기본으로 "USER" 권한 설정)
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
