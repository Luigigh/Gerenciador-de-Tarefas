package br.com.luigifalconi.taskmanager.controller;


import br.com.luigifalconi.taskmanager.dto.request.UserRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.UserResponseDTO;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.mapper.UserMapper;
import br.com.luigifalconi.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO){

        // Esse zé aqui converte do Request pra User
        User user = userMapper.toEntity(userRequestDTO);

        //Esse zé aqui salva na service o user convertido do Mapper->Entity.
        User savedUser = userService.createUser(user);

        //Esse zé aqui converte o User na userResponse
        UserResponseDTO userResponseDTO = userMapper.toReponseDTO(savedUser);

        // Esse ze aqui efetuaria o salvamento do savedUser usando o userRepository no banco.
        return userResponseDTO;

    }

}
