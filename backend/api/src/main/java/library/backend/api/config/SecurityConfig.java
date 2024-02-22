package library.backend.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                // disable cors
                http.cors(AbstractHttpConfigurer::disable);

                // disable csrf
                http.csrf(AbstractHttpConfigurer::disable);

                // HTTP Request Filter
                http.authorizeHttpRequests(
                                requestMatcher -> requestMatcher
                                                .requestMatchers("/api/auth/login/**").permitAll()
                                                .requestMatchers("/api/auth/signup/**").permitAll()
                                                .requestMatchers("/error/**").permitAll()
                                                .anyRequest().authenticated());

                // Authentication Entry Point -> Exception Handler
                http.exceptionHandling(
                                exceptionConfig -> exceptionConfig.authenticationEntryPoint(
                                                authenticationEntryPoint));

                // Set stateless session policy
                http.sessionManagement(
                                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // Add JWT authentication filter
                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
