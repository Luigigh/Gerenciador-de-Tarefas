package br.com.luigifalconi.taskmanager.dto.response;

import br.com.luigifalconi.taskmanager.enums.RoleUser;
import br.com.luigifalconi.taskmanager.enums.StatusUser;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstname;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birth;

    private RoleUser role;

    private StatusUser status;

    private LocalDateTime createdAt;

}
