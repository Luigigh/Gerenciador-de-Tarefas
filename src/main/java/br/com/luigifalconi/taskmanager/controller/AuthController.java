package br.com.luigifalconi.taskmanager.controller;


import br.com.luigifalconi.taskmanager.dto.request.LoginRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.LoginResponseDTO;
import br.com.luigifalconi.taskmanager.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;


    public AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public LoginResponseDTO authLogin(@RequestBody LoginRequestDTO loginRequestDTO){

        LoginResponseDTO response = authenticationService.authenticate(loginRequestDTO);

    return response;
    }

}
