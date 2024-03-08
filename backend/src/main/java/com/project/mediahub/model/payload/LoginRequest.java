package com.project.mediahub.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email(message = "Please provide a valid email")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
