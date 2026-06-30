package br.com.luigifalconi.taskmanager.service;

import br.com.luigifalconi.taskmanager.dto.request.LoginRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.LoginResponseDTO;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import br.com.luigifalconi.taskmanager.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService
            (UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {


        User user = userRepository.findUserByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordCorrect = passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword());

        if (!passwordCorrect) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(token);
    }

}
