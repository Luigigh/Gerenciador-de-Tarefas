# Gerenciador de Tarefas — API

Backend RESTful desenvolvido para gerenciamento de projetos, tarefas e usuários, integrando autenticação stateless via JWT, controle de acesso baseado em papéis (RBAC) e proteção ativa contra ataques de força bruta.

> Repositório do front-end (React + TypeScript): [Gerenciador-de-Tarefas-Web](https://github.com/Luigigh/Gerenciador-de-Tarefas-Web)

### 🔑 Acesso de Demonstração (Recrutadores & Avaliadores)

Para explorar a plataforma com permissões seguras de visualização (Role: `USER`):

* **E-mail:** `user@example.com`
* **Senha:** `123456`

> *Nota: Usuários com o perfil `USER` possuem permissões restritas a leitura via RBAC.*

---

## Tecnologias

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security, JJWT (io.jsonwebtoken) e Bucket4j
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** MySQL 8
* **Documentação:** OpenAPI 3 / Swagger (SpringDoc)
* **Build Tool:** Maven
* **Containerização:** Docker / Docker Compose

---

## Recursos de Segurança

* **Autenticação Stateless:** Emissão e validação de tokens JWT com expiração configurada.
* **Proteção contra Brute Force (Rate Limiting):** Implementação do algoritmo *Token Bucket* com **Bucket4j** interceptando requisições em `/auth/login` (máximo de 5 tentativas por minuto por IP com retorno HTTP `429 Too Many Requests`).
* **Criptografia de Senhas:** Hashing unidirecional com `BCryptPasswordEncoder`.

---

## Controle de Acesso (RBAC)

O controle de acesso é validado tanto na cadeia de filtros (`SecurityConfig`) quanto nos controllers via anotações `@PreAuthorize`.

* **ADMIN:** Acesso irrestrito ao sistema (gerencia usuários, projetos e tarefas).
* **MANAGER:** Gerencia projetos e tarefas (criação, edição e exclusão); visualiza usuários da equipe.
* **DEVELOPER / TESTER:** Visualiza projetos; edita status e detalhes de tarefas atribuídas.
* **USER:** Visualização de leitura (*read-only*) em projetos e tarefas.

### 🛡️ Homologação de Segurança e RBAC

O relatório completo de validação da camada de segurança, políticas de CORS, controle de acesso baseado em papéis (RBAC) e proteção contra força bruta via Rate Limiting está documentado externamente:

📄 **[Acessar Relatório Técnico de Homologação (Google Drive)](https://drive.google.com/file/d/1kwbEOP7f4VYRbPWbeoDnFSPURcFNlA1u/view?usp=sharing)**


---

## Variáveis de Ambiente

As configurações de banco e autenticação podem ser sobrescritas no ambiente. Valores padrão configurados para execução via Docker:

```properties
# Banco de Dados
DB_URL=jdbc:mysql://localhost:3306/taskmanager_db
DB_USERNAME=taskmanager_user
DB_PASSWORD=taskmanager_dev_only

# Segurança (JWT)
JWT_SECRET=sua_chave_secreta_aqui