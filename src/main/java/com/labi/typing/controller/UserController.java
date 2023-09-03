package com.labi.typing.controller;

import com.labi.typing.DTO.user.*;
import com.labi.typing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO dto) {
        userService.saveUser(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid UserResetPasswordDTO dto) {
        userService.resetPassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/profile/username")
    public ResponseEntity<?> updateUsername(@RequestBody @Valid UserUpdateUsernameDTO dto,
                                            @RequestHeader("Authorization") String authHeader) {
        userService.updateUsername(dto, authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/profile/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UserUpdatePasswordDTO dto,
                                            @RequestHeader("Authorization") String authHeader) {
        userService.updatePassword(dto, authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<?> deleteAccount(@RequestBody @Valid UserDeleteAccountDTO dto,
                                           @RequestHeader("Authorization") String authHeader) {
        userService.deleteUser(dto, authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
