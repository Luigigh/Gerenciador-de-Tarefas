package br.com.luigifalconi.taskmanager.controller;


import br.com.luigifalconi.taskmanager.dto.request.UserRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.UserResponseDTO;
import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.mapper.UserMapper;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import br.com.luigifalconi.taskmanager.service.UserService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        UserResponseDTO userResponseDTO = userMapper.toResponseDTO(savedUser);

        // Esse ze aqui efetuaria o salvamento do savedUser usando o userRepository no banco.
        return userResponseDTO;

    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){

        User user = userService.findUserById(id);

        return userMapper.toResponseDTO(user);

    }


    public List<UserResponseDTO> getAllUsers(){

        List<User> users = userService.findAllUsers();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for (User user : users){

            UserResponseDTO userDTO = userMapper.toResponseDTO(user);

            userResponseDTOList.add(userDTO);

        }

        return userResponseDTOList;

    }








}
