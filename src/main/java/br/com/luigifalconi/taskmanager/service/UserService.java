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

    public void validateEmailUserUpdate(User user){

        Optional<User> userFound = userRepository.findUserByEmail(user.getEmail());

        if (userFound.isPresent() && !userFound.get().getIdUser().equals(user.getIdUser())){
            throw new RuntimeException("Email already in use!");
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


    public User updateUser(Long id, UserUpdateDTO userUpdateDTO){

        
        User userFound = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
        
        userFound.setFirstName(userUpdateDTO.getFirstname());
        userFound.setLastName(userUpdateDTO.getLastName());
        userFound.setEmail(userUpdateDTO.getEmail());
        userFound.setPhone(userUpdateDTO.getPhone());
        userFound.setDateBirth(userUpdateDTO.getBirth());

        validateEmailUserUpdate(userFound);


        return userRepository.save(userFound);
    }

    public User deleteUser(Long id){
        User userFound = userRepository.findBy(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (userFound.getStatus().equals(StatusUser.INACTIVE)){
            throw new RuntimeException("User already deleted!");
        }
        
        userFound.setStatus(StatusUser.INACTIVE);
        
        return userRepository.save(userFound);
    }


    

}
