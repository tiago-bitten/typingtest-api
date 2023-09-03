package com.labi.typing.DTO.user;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteAccountDTO(
        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmPassword
) {
}
