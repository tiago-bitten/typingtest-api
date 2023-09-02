package com.labi.typing.DTO.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserUpdatePasswordDTO(
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, message = "Password must be at least 6 characters")
    String password,

    @NotBlank(message = "Confirm Password cannot be blank")
    String confirmPassword
) {
}
