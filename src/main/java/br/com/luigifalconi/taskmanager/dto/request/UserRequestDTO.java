package br.com.luigifalconi.taskmanager.dto.request;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {


    private String firstname;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private LocalDate birth;


}
