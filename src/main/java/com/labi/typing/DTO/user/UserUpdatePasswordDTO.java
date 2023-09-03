package com.labi.typing.DTO.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserUpdatePasswordDTO(
        @NotBlank(message = "Current password cannot be blank")
        String currentPassword,

        @NotBlank(message = "Password cannot be blank")
        @Length(min = 6, message = "Password must be at least 6 characters")
        String newPassword,

        @NotBlank(message = "Confirm Password cannot be blank")
        String confirmNewPassword
) {
}
