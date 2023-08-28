package com.labi.typing.DTO;

import com.labi.typing.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDTO(
        @NotBlank(message = "Username is required") @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email address")
        String email,
        @NotBlank(message = "Password is required") @Length(min = 6, message = "Password must be at least 6 characters")
        String password,
        @NotBlank(message = "Role is required")
        UserRole userRole
) {
}
