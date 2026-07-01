package br.com.luigifalconi.taskmanager.mapper;

import br.com.luigifalconi.taskmanager.dto.request.UserRequestDTO;
import br.com.luigifalconi.taskmanager.dto.response.UserResponseDTO;
import br.com.luigifalconi.taskmanager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO requestDTO){

        User user = new User();

        user.setFirstName(requestDTO.getFirstname());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setPhone(requestDTO.getPhone());
        user.setDateBirth(requestDTO.getBirth());

        return user;

    }


    public UserResponseDTO toResponseDTO(User user){

        UserResponseDTO userResDTO = new UserResponseDTO();

        userResDTO.setId(user.getIdUser());
        userResDTO.setFirstname(user.getFirstName());
        userResDTO.setLastName(user.getLastName());
        userResDTO.setEmail(user.getEmail());
        userResDTO.setPhone(user.getPhone());
        userResDTO.setBirth(user.getDateBirth());
        userResDTO.setRole(user.getRole());
        userResDTO.setStatus(user.getStatus());
        userResDTO.setCreatedAt(user.getCreatedAt());

        return userResDTO;

    }
}
