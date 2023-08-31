package com.labi.typing.service;

import com.labi.typing.DTO.UserRegisterDTO;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserRegisterDTO userRegisterDTO) {
        if (findByUsername(userRegisterDTO.username()) != null) throw new ValidationException("Username already exists");
        if (findByEmail(userRegisterDTO.email()) != null) throw new ValidationException("Email already exists");

        User user = mapUserRegisterDTOToUser(userRegisterDTO);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        return new User(userRegisterDTO.username(), userRegisterDTO.email(), userRegisterDTO.password(), userRegisterDTO.userRole());
    }
}
