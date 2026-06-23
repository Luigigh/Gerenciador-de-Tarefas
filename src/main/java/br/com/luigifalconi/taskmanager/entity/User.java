package br.com.luigifalconi.taskmanager.entity;

import br.com.luigifalconi.taskmanager.enums.RoleUser;
import br.com.luigifalconi.taskmanager.enums.StatusUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @NotBlank
    private String firstname;
    @NotNull
    private String lastname;

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
    private String phone;
    private LocalDate dateBirth;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
