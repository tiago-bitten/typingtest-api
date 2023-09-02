package com.labi.typing.controller;

import com.labi.typing.DTO.user.UserUpdateUsernameDTO;
import com.labi.typing.DTO.user.UserRegisterDTO;
import com.labi.typing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.saveUser(userRegisterDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/profile/username")
    public ResponseEntity<?> updateUsername(@RequestBody @Valid UserUpdateUsernameDTO userProfileDTO,
                                            @RequestHeader("Authorization") String authHeader) {
        userService.updateUsername(userProfileDTO, authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
