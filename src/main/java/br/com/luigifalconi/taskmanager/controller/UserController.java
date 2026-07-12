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



    @ApiOperation(summary = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "User not created")
    })
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



    @ApiOperation(summary = "Get a user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){

        User user = userService.findUserById(id);

        return userMapper.toResponseDTO(user);

    }




    @ApiOperation(summary = "Get all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users found successfully"),
        @ApiResponse(responseCode = "404", description = "Users not found")
    })
    public List<UserResponseDTO> getAllUsers(){

        List<User> users = userService.findAllUsers();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for (User user : users){

            UserResponseDTO userDTO = userMapper.toResponseDTO(user);

            userResponseDTOList.add(userDTO);

        }

        return userResponseDTOList;

    }


    

    @PutMapping("/{id}")
    @Operation(summary = "Update a user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO){
        User user = userMapper.toEntity(userUpdateDTO);
        User updatedUser = userService.updateUser(user);
        return userMapper.toResponseDTO(updatedUser);
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDTO deleteUser(@PathVariable Long id){
        User deletedUser = userService.deleteUser(id);
        return userMapper.toResponseDTO(deletedUser);
    }








}
