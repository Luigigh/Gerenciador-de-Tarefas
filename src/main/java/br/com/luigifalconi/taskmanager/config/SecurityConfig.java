package br.com.luigifalconi.taskmanager.config;

import br.com.luigifalconi.taskmanager.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

private final JwtAuthenticationFilter jwtAuthenticationFilter;

public SecurityConfig(
        JwtAuthenticationFilter jwtAuthenticationFilter
) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
}

@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http
) throws Exception {

    http
            .csrf(
                    csrf -> csrf.disable()
            )

            .cors(
                    Customizer.withDefaults()
            )

            .sessionManagement(
                    session -> session
                            .sessionCreationPolicy(
                                    SessionCreationPolicy.STATELESS
                            )
            )

            .authorizeHttpRequests(
                    auth -> auth

                            // Rotas públicas
                            .requestMatchers(
                                    "/auth/**"
                            )
                            .permitAll()

                            // Apenas ADMIN pode gerenciar usuários
                            .requestMatchers(
                                    "/users/**"
                            )
                            .hasRole("ADMIN")

                            // ADMIN e MANAGER podem criar,
                            // editar e excluir projetos
                            .requestMatchers(
                                    HttpMethod.POST,
                                    "/projects/**"
                            )
                            .hasAnyRole(
                                    "ADMIN",
                                    "MANAGER"
                            )

                            .requestMatchers(
                                    HttpMethod.PUT,
                                    "/projects/**"
                            )
                            .hasAnyRole(
                                    "ADMIN",
                                    "MANAGER"
                            )

                            .requestMatchers(
                                    HttpMethod.DELETE,
                                    "/projects/**"
                            )
                            .hasAnyRole(
                                    "ADMIN",
                                    "MANAGER"
                            )

                            // Todos os usuários autenticados
                            // podem visualizar projetos
                            .requestMatchers(
                                    HttpMethod.GET,
                                    "/projects/**"
                            )
                            .authenticated()

                            // ADMIN e MANAGER podem criar tasks
                            .requestMatchers(
                                HttpMethod.POST,
                                    "/tasks/**"
                            )
                            .hasAnyRole(
                                    "ADMIN",
                                    "MANAGER"
                            )

                            // ADMIN, MANAGER, DEVELOPER
                            // e TESTER podem atualizar tasks
                            .requestMatchers(
                                HttpMethod.PUT,
                                    "/tasks/**"
                            )
                            .hasAnyRole(
                                    "ADMIN",
                                    "MANAGER",
                                    "DEVELOPER",
                                    "TESTER"
                            )

                            // Apenas ADMIN e MANAGER
                            // podem excluir tasks
                            .requestMatchers(
                                HttpMethod.DELETE,
                                    "/tasks/**"
                            )
                            .hasAnyRole(
                                    "ADMIN",
                                    "MANAGER"
                            )

                            // Todos os usuários autenticados
                            // podem visualizar tasks
                            .requestMatchers(
                                HttpMethod.GET,
                                    "/tasks/**"
                            )
                            .authenticated()

                            // Qualquer outra rota exige login
                            .anyRequest()
                            .authenticated()
            )

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

    return http.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

}
