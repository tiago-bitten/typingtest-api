package com.labi.typing.DTO;

import com.labi.typing.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDTO(
        @NonNull @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,
        @NonNull @Email(message = "Invalid email address")
        String email,
        @NonNull @Length(min = 6, message = "Password must be at least 6 characters")
        String password,
        @NotBlank(message = "Role must be informed")
        UserRole userRole
) {
}
