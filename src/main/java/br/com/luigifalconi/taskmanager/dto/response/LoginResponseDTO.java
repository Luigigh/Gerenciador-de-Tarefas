package br.com.luigifalconi.taskmanager.dto.response;

import br.com.luigifalconi.taskmanager.enums.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

private String token;

private Long idUser;

private String firstName;

private String lastName;

private String email;

private RoleUser role;

}
