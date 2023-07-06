package com.example.rest_api.config

import com.example.rest_api.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider,
    private val logoutHandler: LogoutHandler
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/user/**")
                .permitAll()
                .requestMatchers(GET, "/api/tasks/**")
                .hasAnyRole(Role.ROLE_ADMIN.name, Role.ROLE_MANAGER.name, Role.ROLE_USER.name)
                .requestMatchers(POST,  "/api/tasks/**")
                .hasAnyRole(Role.ROLE_ADMIN.name)
                .requestMatchers(PUT,  "/api/tasks/**")
                .hasAnyRole(Role.ROLE_ADMIN.name, Role.ROLE_MANAGER.name)
                .requestMatchers(PATCH, "/api/tasks/**")
                .hasAnyRole(Role.ROLE_ADMIN.name, Role.ROLE_MANAGER.name)
                .requestMatchers(DELETE, "/api/tasks/**")
                .hasAnyRole(Role.ROLE_ADMIN.name)
                .anyRequest()
                .authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
                .logout()
                .logoutUrl("/api/user/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler { _, _, _ -> SecurityContextHolder.clearContext() }

        return http.build()
    }
}