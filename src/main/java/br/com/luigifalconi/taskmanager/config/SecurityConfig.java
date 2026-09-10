package br.com.luigifalconi.taskmanager.config;

import br.com.luigifalconi.taskmanager.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Fonte da verdade do RBAC da aplicação.
 * <p>
 * Matriz de permissões (ver documentação do projeto):
 *
 * <pre>
 * Recurso                          | ADMIN | MANAGER | DEVELOPER | TESTER | USER
 * ----------------------------------------------------------------------------
 * Usuários  - criar/editar/excluir |  sim  |   não   |    não    |  não   | não
 * Usuários  - listar/ver           |  sim  |   sim   |    não    |  não   | não
 * Projetos  - criar/editar/excluir |  sim  |   sim   |    não    |  não   | não
 * Projetos  - ver                  |  sim  |   sim   |    sim    |  sim   | sim
 * Tasks     - criar/excluir        |  sim  |   sim   |    não    |  não   | não
 * Tasks     - editar               |  sim  |   sim   |    sim    |  sim   | não
 * Tasks     - ver                  |  sim  |   sim   |    sim    |  sim   | sim
 * </pre>
 * <p>
 * {@code @EnableMethodSecurity} habilita a avaliação de {@code @PreAuthorize}
 * nos controllers. Sem essa annotation o Spring Security IGNORA
 * silenciosamente qualquer {@code @PreAuthorize}, então os controllers devem
 * usar essa camada apenas para reforçar/documentar a mesma regra definida
 * aqui — nunca para definir uma regra diferente da configurada nesta classe.
 */
@Configuration
@EnableMethodSecurity
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

                                // ==========================================
                                // USERS
                                // ==========================================

                                // ADMIN e MANAGER podem listar/ver usuários
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/users/**"
                                )
                                .hasAnyRole(
                                        "ADMIN",
                                        "MANAGER"
                                )

                                // Apenas ADMIN pode criar, editar
                                // e excluir usuários
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/users/**"
                                )
                                .hasRole("ADMIN")

                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/users/**"
                                )
                                .hasRole("ADMIN")

                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/users/**"
                                )
                                .hasRole("ADMIN")

                                // ==========================================
                                // PROJECTS
                                // ==========================================

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

                                // ==========================================
                                // TASKS
                                // ==========================================

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
                                // (USER não edita tasks)
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