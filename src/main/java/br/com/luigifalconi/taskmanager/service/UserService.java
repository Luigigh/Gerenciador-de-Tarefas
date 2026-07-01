package br.com.luigifalconi.taskmanager.service;

import br.com.luigifalconi.taskmanager.entity.User;
import br.com.luigifalconi.taskmanager.enums.RoleUser;
import br.com.luigifalconi.taskmanager.enums.StatusUser;
import br.com.luigifalconi.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(RoleUser.USER);

        user.setStatus(StatusUser.ACTIVE);

        return userRepository.save(user);

    }

    //Get pelo ID
    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }


    public List<User> findAllUsers(){
        return userRepository.findAll();
    }


}
