package br.com.luigifalconi.taskmanager.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    private Long id;

    private String firstname;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birth;

}
