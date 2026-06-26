package br.com.luigifalconi.taskmanager.service;

import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Métodos usados na Criação do USER

    public void validateEmailUser(User user){

        Optional<User> userFound = userRepository.findUserByEmail(user.getEmail());

        if (userFound.isPresent()){
            throw new RuntimeException("Email already exists!");
        }
    }

    public User createUser(User user) {

        validateEmailUser(user);
        return userRepository.save(user);

    }


}
