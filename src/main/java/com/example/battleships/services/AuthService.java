package com.example.battleships.services;

import com.example.battleships.models.User;
import com.example.battleships.models.dto.LoginDTO;
import com.example.battleships.models.dto.UserRegistrationDTO;
import com.example.battleships.repositories.UserRepository;
import com.example.battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private final LoggedUser userSession;

    public AuthService(UserRepository userRepository, LoggedUser userSession) {

        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean register(UserRegistrationDTO registrationDTO){
    if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
        return false;
    }

    //duplicate email
        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }

        Optional<User> byUsername = this.userRepository.findByUsername(registrationDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }


        User user=new User();
    user.setUsername(registrationDTO.getUsername());
    user.setEmail(registrationDTO.getEmail());
    user.setFullName(registrationDTO.getFullName());
    user.setPassword(registrationDTO.getPassword());
    this.userRepository.save(user);
    return  true;
    }

    public boolean login(LoginDTO loginDTO) {
        Optional<User> user = this.userRepository.findByUsernameAndPassword(
                loginDTO.getUsername(),
                loginDTO.getPassword());
        if(user.isEmpty()){
            return  false;
        }
        //actual login
        this.userSession.login(user.get());
        return  true;
    }

    public boolean isLoggedIn() {
        return this.userSession.getId() > 0;
    }
}
