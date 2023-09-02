package com.labi.typing.DTO.user;

import com.labi.typing.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDTO(
        @NotBlank(message = "Username cannot be blank")
        @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Length(min = 6, message = "Password must be at least 6 characters")
        String password
) {
}
