package com.labi.typing.DTO.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserUpdateUsernameDTO(
        @NotBlank(message = "Password cannot be blank")
        String currentPassword,

        @NotBlank(message = "Username cannot be blank")
        @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String newUsername
) {
}
