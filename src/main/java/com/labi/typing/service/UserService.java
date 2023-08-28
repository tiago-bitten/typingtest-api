package com.labi.typing.service;

import com.labi.typing.DTO.UserRegisterDTO;
import com.labi.typing.model.User;
import com.labi.typing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserRegisterDTO userRegisterDTO) {
        if (findByUsername(userRegisterDTO.username()) != null) {
            throw new RuntimeException("Username already exists");
        }

        if (findByEmail(userRegisterDTO.email()) != null) {
            throw new RuntimeException("Email already exists");
        }

        userRepository.save(mapUserRegisterDTOToUser(userRegisterDTO));
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
