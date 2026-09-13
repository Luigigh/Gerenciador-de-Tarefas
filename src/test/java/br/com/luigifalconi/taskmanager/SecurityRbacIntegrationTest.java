package br.com.luigifalconi.taskmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityRbacIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // 1. ROTAS PÚBLICAS (Swagger & Auth)

    @Test
    @DisplayName("Deve permitir acesso público à documentação OpenAPI sem autenticação")
    void shouldAllowPublicAccessToOpenApiDocs() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    // 2. ROTAS PROTEGIDAS SEM TOKEN (Acesso Anônimo)

    @Test
    @DisplayName("Deve bloquear requisição não autenticada para /projects com 403 Forbidden")
    void shouldBlockAnonymousAccessToProjects() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve bloquear requisição não autenticada para /tasks com 403 Forbidden")
    void shouldBlockAnonymousAccessToTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isForbidden());
    }

    // 3. REGRAS DE RBAC: Papel 'USER' (Permissões de Leitura)

    @Test
    @WithMockUser(username = "guest@example.com", roles = {"USER"})
    @DisplayName("Usuário com papel USER deve conseguir listar projetos (GET)")
    void shouldAllowUserRoleToReadProjects() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "guest@example.com", roles = {"USER"})
    @DisplayName("Usuário com papel USER deve ser proibido (403) de criar projetos (POST)")
    void shouldForbidUserRoleFromCreatingProjects() throws Exception {
        String newProjectJson = """
            {
                "name": "Projeto Não Autorizado",
                "description": "Teste RBAC",
                "budget": 5000.00
            }
        """;

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProjectJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "guest@example.com", roles = {"USER"})
    @DisplayName("Usuário com papel USER deve ser proibido (403) de deletar tarefas (DELETE)")
    void shouldForbidUserRoleFromDeletingTasks() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isForbidden());
    }

    // 4. REGRAS DE RBAC: Papel 'ADMIN' (Permissões Administrativas)

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @DisplayName("Administrador (ADMIN) tem permissão de acesso ao gerenciamento de usuários (GET /users)")
    void shouldAllowAdminToAccessUsersList() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "guest@example.com", roles = {"USER"})
    @DisplayName("Usuário comum (USER) não pode acessar gerenciamento de usuários (GET /users)")
    void shouldForbidUserFromAccessingUsersList() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }
}