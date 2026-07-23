package br.com.luigifalconi.taskmanager.config;

import br.com.luigifalconi.taskmanager.entity.Project;
import br.com.luigifalconi.taskmanager.enums.StatusProject;
import br.com.luigifalconi.taskmanager.repository.ProjectRepository;

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
    CommandLineRunner createAdmin(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  ProjectRepository projectRepository) {
    
                                    return args -> {

                                        System.out.println("1 - Entrou no CommandLineRunner");
                                
                                        if (!userRepository.existsByRole(RoleUser.ADMIN)) {
                                
                                            System.out.println("2 - Vai criar ADMIN");
                                
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


                                
                                
                                        if (!projectRepository.existsByStatus(StatusProject.NOT_STARTED)) {
                                
                                            System.out.println("Vai criar Projeto");
                                
                                            Project project = new Project();
                                
                                            project.setName("Projeto 1");
                                            project.setDescription("Projeto inicial do TaskManager");
                                            project.setBudget(new BigDecimal("0.0"));                                            
                                            project.setExpectedFinalDate(LocalDate.of(2026, 9, 30));
                                            project.setEndDate(null);
                                            project.setStatus(StatusProject.NOT_STARTED);
                                
                                            projectRepository.save(project);
                                
                                            System.out.println("Projeto criado com sucesso!");
                                
                                        } else {
                                
                                            System.out.println("Projeto já existe.");
                                
                                        }
                                
                                    };
    }



}
