package br.com.luigifalconi.taskmanager.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.enums.PriorityTask;
import br.com.luigifalconi.taskmanager.enums.RoleUser;
import br.com.luigifalconi.taskmanager.enums.StatusProject;
import br.com.luigifalconi.taskmanager.enums.StatusTask;
import br.com.luigifalconi.taskmanager.enums.StatusUser;
import br.com.luigifalconi.taskmanager.repository.ProjectRepository;
import br.com.luigifalconi.taskmanager.repository.TaskRepository;
import br.com.luigifalconi.taskmanager.repository.UserRepository;

@Configuration
public class AdminInitializer {


@Bean
CommandLineRunner createInitialData(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        ProjectRepository projectRepository,
        TaskRepository taskRepository
) {

    return args -> {

        System.out.println(
                "[INITIALIZER] Iniciando dados iniciais..."
        );

        // ==================================================
        // USUÁRIOS
        // ==================================================

        User admin = createUserIfNotExists(
                userRepository,
                passwordEncoder,
                "Luigi",
                "Bruno",
                "luigi@gmail.com",
                "123456",
                "5516999948765",
                LocalDate.of(2003, 10, 31),
                RoleUser.ADMIN
        );

        User manager = createUserIfNotExists(
                userRepository,
                passwordEncoder,
                "Mariana",
                "Silva",
                "mariana.manager@gmail.com",
                "123456",
                "5516999911111",
                LocalDate.of(1995, 5, 12),
                RoleUser.MANAGER
        );

        User developer = createUserIfNotExists(
                userRepository,
                passwordEncoder,
                "Carlos",
                "Souza",
                "carlos.developer@gmail.com",
                "123456",
                "5516999922222",
                LocalDate.of(1998, 8, 20),
                RoleUser.DEVELOPER
        );

        User tester = createUserIfNotExists(
                userRepository,
                passwordEncoder,
                "Ana",
                "Oliveira",
                "ana.tester@gmail.com",
                "123456",
                "5516999933333",
                LocalDate.of(1999, 3, 15),
                RoleUser.TESTER
        );

        User user = createUserIfNotExists(
                userRepository,
                passwordEncoder,
                "Pedro",
                "Almeida",
                "pedro.user@gmail.com",
                "123456",
                "5516999944444",
                LocalDate.of(2000, 11, 8),
                RoleUser.USER
        );

        // ==================================================
        // PROJETOS
        // ==================================================

        Project projectTaskManager =
                createProjectIfNotExists(
                        projectRepository,
                        "TaskManager",
                        "Sistema para gerenciamento de projetos e tarefas.",
                        new BigDecimal("15000.00"),
                        LocalDate.of(2026, 7, 1),
                        LocalDate.of(2026, 10, 30),
                        null,
                        StatusProject.IN_PROGRESS
                );

        Project projectEcommerce =
                createProjectIfNotExists(
                        projectRepository,
                        "E-Commerce",
                        "Plataforma de vendas online.",
                        new BigDecimal("25000.00"),
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 12, 15),
                        null,
                        StatusProject.NOT_STARTED
                );

        Project projectMobile =
                createProjectIfNotExists(
                        projectRepository,
                        "Aplicativo Mobile",
                        "Aplicativo mobile para acompanhamento de tarefas.",
                        new BigDecimal("18000.00"),
                        LocalDate.of(2026, 6, 10),
                        LocalDate.of(2026, 9, 20),
                        null,
                        StatusProject.REVIEW
                );

        Project projectWebsite =
                createProjectIfNotExists(
                        projectRepository,
                        "Portal Institucional",
                        "Novo portal institucional da empresa.",
                        new BigDecimal("12000.00"),
                        LocalDate.of(2026, 4, 1),
                        LocalDate.of(2026, 6, 30),
                        LocalDate.of(2026, 6, 28),
                        StatusProject.COMPLETED
                );

        // ==================================================
        // TASKS
        // ==================================================

        if (taskRepository.count() == 0) {

            System.out.println(
                    "[INITIALIZER] Criando Tasks iniciais..."
            );

            createTask(
                    taskRepository,
                    "Configurar autenticação JWT",
                    "Implementar autenticação e autorização utilizando JWT.",
                    "Validar login e proteção das rotas.",
                    LocalDate.of(2026, 7, 20),
                    LocalDate.of(2026, 7, 28),
                    null,
                    StatusTask.IN_PROGRESS,
                    PriorityTask.CRITICAL,
                    projectTaskManager,
                    admin
            );

            createTask(
                    taskRepository,
                    "Criar quadro Kanban",
                    "Desenvolver o quadro Kanban para organização das Tasks.",
                    "Interface baseada em colunas por status.",
                    LocalDate.of(2026, 7, 25),
                    LocalDate.of(2026, 8, 5),
                    null,
                    StatusTask.IN_PROGRESS,
                    PriorityTask.HIGH,
                    projectTaskManager,
                    developer
            );

            createTask(
                    taskRepository,
                    "Revisar endpoints da API",
                    "Testar e revisar os endpoints de Users, Projects e Tasks.",
                    "Verificar respostas e códigos HTTP.",
                    LocalDate.of(2026, 7, 18),
                    LocalDate.of(2026, 7, 30),
                    null,
                    StatusTask.REVIEW,
                    PriorityTask.MEDIUM,
                    projectTaskManager,
                    tester
            );

            createTask(
                    taskRepository,
                    "Criar tela de produtos",
                    "Desenvolver a interface de listagem de produtos.",
                    "Aguardando início do desenvolvimento.",
                    LocalDate.of(2026, 8, 1),
                    LocalDate.of(2026, 8, 20),
                    null,
                    StatusTask.NOT_STARTED,
                    PriorityTask.HIGH,
                    projectEcommerce,
                    developer
            );

            createTask(
                    taskRepository,
                    "Definir requisitos do E-Commerce",
                    "Levantar os requisitos funcionais da plataforma.",
                    "Reunião com a equipe de negócio pendente.",
                    LocalDate.of(2026, 8, 2),
                    LocalDate.of(2026, 8, 10),
                    null,
                    StatusTask.NOT_STARTED,
                    PriorityTask.MEDIUM,
                    projectEcommerce,
                    manager
            );

            createTask(
                    taskRepository,
                    "Validar versão mobile",
                    "Executar testes funcionais no aplicativo mobile.",
                    "Aguardando aprovação final.",
                    LocalDate.of(2026, 7, 5),
                    LocalDate.of(2026, 7, 25),
                    null,
                    StatusTask.REVIEW,
                    PriorityTask.HIGH,
                    projectMobile,
                    tester
            );

            createTask(
                    taskRepository,
                    "Corrigir navegação do aplicativo",
                    "Corrigir problemas encontrados na navegação.",
                    "Correções concluídas e enviadas para revisão.",
                    LocalDate.of(2026, 7, 10),
                    LocalDate.of(2026, 7, 22),
                    LocalDate.of(2026, 7, 21),
                    StatusTask.COMPLETED,
                    PriorityTask.MEDIUM,
                    projectMobile,
                    developer
            );

            createTask(
                    taskRepository,
                    "Publicar portal institucional",
                    "Realizar a publicação do novo portal.",
                    "Portal publicado com sucesso.",
                    LocalDate.of(2026, 6, 20),
                    LocalDate.of(2026, 6, 28),
                    LocalDate.of(2026, 6, 28),
                    StatusTask.COMPLETED,
                    PriorityTask.LOW,
                    projectWebsite,
                    user
            );

            System.out.println(
                    "[INITIALIZER] Tasks criadas com sucesso!"
            );

        } else {

            System.out.println(
                    "[INITIALIZER] Já existem Tasks cadastradas."
            );

        }

        System.out.println(
                "[INITIALIZER] Dados iniciais finalizados!"
        );

    };

}

private User createUserIfNotExists(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        LocalDate dateBirth,
        RoleUser role
) {

    return userRepository
            .findUserByEmail(email)
            .orElseGet(() -> {

                User user = new User();

                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(
                        passwordEncoder.encode(password)
                );
                user.setPhone(phone);
                user.setDateBirth(dateBirth);
                user.setRole(role);
                user.setStatus(
                        StatusUser.ACTIVE
                );

                User savedUser =
                        userRepository.save(user);

                System.out.println(
                        "[INITIALIZER] Usuário criado: "
                                + email
                );

                return savedUser;

            });

}

private Project createProjectIfNotExists(
        ProjectRepository projectRepository,
        String name,
        String description,
        BigDecimal budget,
        LocalDate startDate,
        LocalDate expectedFinalDate,
        LocalDate endDate,
        StatusProject status
) {

    return projectRepository
            .findByName(name)
            .orElseGet(() -> {

                Project project =
                        new Project();

                project.setName(name);
                project.setDescription(
                        description
                );
                project.setBudget(budget);
                project.setStartDate(
                        startDate
                );
                project.setExpectedFinalDate(
                        expectedFinalDate
                );
                project.setEndDate(
                        endDate
                );
                project.setStatus(status);

                Project savedProject =
                        projectRepository.save(
                                project
                        );

                System.out.println(
                        "[INITIALIZER] Projeto criado: "
                                + name
                );

                return savedProject;

            });

}

private void createTask(
        TaskRepository taskRepository,
        String title,
        String description,
        String comment,
        LocalDate startDate,
        LocalDate expectedFinalDate,
        LocalDate endDate,
        StatusTask status,
        PriorityTask priority,
        Project project,
        User responsible
) {

    Task task = new Task();

    task.setTitle(title);
    task.setDescription(
            description
    );
    task.setComment(comment);
    task.setStartDate(
            startDate
    );
    task.setExpectedFinalDate(
            expectedFinalDate
    );
    task.setEndDate(
            endDate
    );
    task.setStatus(status);
    task.setPriority(priority);
    task.setProject(project);
    task.setResponsible(
            responsible
    );

    taskRepository.save(task);

    System.out.println(
            "[INITIALIZER] Task criada: "
                    + title
    );

}


}
