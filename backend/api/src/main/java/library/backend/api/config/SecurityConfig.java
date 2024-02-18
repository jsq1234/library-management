package library.backend.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint,
                        JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.authenticationEntryPoint = authenticationEntryPoint;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

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
                                                .requestMatchers("/api/auth/sign-up/**").permitAll()
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

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider(
                        UserDetailsService userDetailsService,
                        PasswordEncoder passwordEncoder) {
                var authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(userDetailsService);
                authenticationProvider.setPasswordEncoder(passwordEncoder);

                return authenticationProvider;
        }

        @Bean
        public PasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
