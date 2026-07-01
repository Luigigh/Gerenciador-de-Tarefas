package br.com.luigifalconi.taskmanager.config;

import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.enums.RoleUser;
import br.com.luigifalconi.taskmanager.enums.StatusUser;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import jakarta.persistence.Enumerated;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class AdminInitializer {


    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){

    return args -> {

        if (userRepository.existsByRole(RoleUser.ADMIN)) {
            return;
        }


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

    };

    }



}
