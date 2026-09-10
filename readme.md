# Gerenciador de Tarefas — API

Backend RESTful desenvolvido para gerenciamento de projetos, tarefas e usuários, integrando autenticação stateless via JWT e controle de acesso baseado em papéis (RBAC).

> Repositório do front-end (React + TypeScript): [Gerenciador-de-Tarefas-Web](https://github.com/Luigigh/Gerenciador-de-Tarefas-Web)

---

## Tecnologias

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security + Auth0 Java-JWT
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** MySQL 8
* **Build tool:** Maven
* **Containerização:** Docker / Docker Compose

---

## Controle de Acesso (RBAC)

O controle de acesso é validado nas rotas (`SecurityConfig`) e nos controllers via `@PreAuthorize`.

* **ADMIN:** Acesso irrestrito ao sistema (gerencia usuários, projetos e tarefas).
* **MANAGER:** Gerencia projetos e tarefas (criação, edição e exclusão); visualiza usuários da equipe.
* **DEVELOPER / TESTER:** Visualiza projetos; edita status e detalhes de tarefas atribuídas.
* **USER:** Visualização de leitura (*read-only*) em projetos e tarefas.

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

---

## Execução Local

### Pré-requisitos

* Java 21 (JDK)
* Docker e Docker Compose (recomendado) ou MySQL 8 instalado localmente