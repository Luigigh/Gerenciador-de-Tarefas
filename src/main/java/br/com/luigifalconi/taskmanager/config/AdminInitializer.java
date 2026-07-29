package br.com.luigifalconi.taskmanager.config;

import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.entity.Task;
import br.com.luigifalconi.taskmanager.enums.StatusProject;
import br.com.luigifalconi.taskmanager.enums.StatusTask;
import br.com.luigifalconi.taskmanager.repository.ProjectRepository;
import br.com.luigifalconi.taskmanager.repository.TaskRepository;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.enums.RoleUser;
import br.com.luigifalconi.taskmanager.enums.StatusUser;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

import java.time.LocalDate;

@Configuration
public class AdminInitializer {


    @Bean
CommandLineRunner createAdmin(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        ProjectRepository projectRepository,
        TaskRepository taskRepository
) {

    return args -> {

        System.out.println("Iniciando dados iniciais...");

        // =========================
        // CRIAR ADMIN
        // =========================

        if (!userRepository.existsByRole(RoleUser.ADMIN)) {

            User admin = new User();

            admin.setFirstName("Luigi");
            admin.setLastName("Bruno");
            admin.setEmail("Luigi@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setDateBirth(LocalDate.of(2003, 10, 31));
            admin.setPhone("5516999948765");
            admin.setRole(RoleUser.ADMIN);
            admin.setStatus(StatusUser.ACTIVE);

            userRepository.save(admin);

            System.out.println("ADMIN criado com sucesso!");

        } else {

            System.out.println("ADMIN já existe.");

        }


        // =========================
        // CRIAR PROJETO
        // =========================

        if (!projectRepository.existsByStatus(StatusProject.NOT_STARTED)) {

            Project project = new Project();

            project.setName("Projeto 1");
            project.setDescription("Projeto inicial do TaskManager");
            project.setBudget(BigDecimal.ZERO);
            project.setStartDate(LocalDate.of(2026, 7, 23));
            project.setExpectedFinalDate(LocalDate.of(2026, 9, 30));
            project.setEndDate(null);
            project.setStatus(StatusProject.NOT_STARTED);

            projectRepository.save(project);

            System.out.println("PROJETO criado com sucesso!");

        } else {

            System.out.println("PROJETO já existe.");

        }


        if (taskRepository.count() == 0) { 

            System.out.println( "[INITIALIZER] Criando Task" ); 

            User adminInicial = userRepository
                .findUserByEmail("Luigi@gmail.com")
                
                .orElseThrow(() ->
                new RuntimeException("Admin não encontrado")
            );

        Project projectInicial = projectRepository
            .findById(1L)

            .orElseThrow(() ->
                new RuntimeException("Projeto não encontrado")
        );
            
            Task task = new Task(); 
            
            task.setTitle( "Desenvolver tela de Login" ); 
            task.setDescription( "Criar a interface de autenticação do sistema" ); 
            task.setComment( "Task inicial criada automaticamente" ); 
            task.setStartDate( LocalDate.now() ); 
            task.setExpectedFinalDate( LocalDate.now().plusDays(7) ); task.setEndDate(null); 
            task.setStatus( StatusTask.IN_PROGRESS ); 
            task.setProject(projectInicial); 
            task.setResponsible(adminInicial); 
            
            taskRepository.save(task); 
            System.out.println( "[INITIALIZER] Task criada" ); 

        } else { 
            System.out.println( "[INITIALIZER] Já existem Tasks" ); 
        }

    };
}


}
