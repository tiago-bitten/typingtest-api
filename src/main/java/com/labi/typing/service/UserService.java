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
        userRepository.save(mapUserRegisterDTOToUser(userRegisterDTO));
    }

    private User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        return new User(userRegisterDTO.username(), userRegisterDTO.email(), userRegisterDTO.password(), userRegisterDTO.userRole());
    }
}
