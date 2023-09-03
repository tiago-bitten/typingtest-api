package com.labi.typing.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserResetPasswordDTO(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Type a valid email address")
        String email
) {
}
