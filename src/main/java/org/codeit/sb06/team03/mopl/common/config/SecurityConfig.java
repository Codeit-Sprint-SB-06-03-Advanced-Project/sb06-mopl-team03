package org.codeit.sb06.team03.mopl.common.config;

import org.codeit.sb06.team03.mopl.account.domain.vo.Role;
import org.codeit.sb06.team03.mopl.common.security.LoginFailureHandler;
import org.codeit.sb06.team03.mopl.common.security.MoplAccessDeniedHandler;
import org.codeit.sb06.team03.mopl.common.security.MoplAuthenticationEntryPoint;
import org.codeit.sb06.team03.mopl.common.security.SpaCsrfTokenRequestHandler;
import org.codeit.sb06.team03.mopl.common.security.jwt.JwtAuthenticationFilter;
import org.codeit.sb06.team03.mopl.common.security.jwt.JwtLoginSuccessHandler;
import org.codeit.sb06.team03.mopl.common.security.jwt.JwtLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            SpaCsrfTokenRequestHandler spaCsrfTokenRequestHandler,
            JwtLoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            MoplAuthenticationEntryPoint authenticationEntryPoint,
            MoplAccessDeniedHandler accessDeniedHandler,
            JwtLogoutHandler logoutHandler
    ) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(spaCsrfTokenRequestHandler)
                        .ignoringRequestMatchers("/h2-console/**")
                        .ignoringRequestMatchers("/v3/api-docs/**", "/swagger-ui/**")
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(auth -> auth
                        // 정적 리소스, h2, swagger
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/", "/index.html", "/favicon.svg", "/assets/**").permitAll()
                        // REST API
                        .requestMatchers(HttpMethod.POST, "/api/auth/sign-in").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/csrf-token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginProcessingUrl("/api/auth/sign-in")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/sign-out")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT)
                        )
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
        ;

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(Role.ADMIN.name())
                .implies(Role.USER.name())
                .build();
    }
}
