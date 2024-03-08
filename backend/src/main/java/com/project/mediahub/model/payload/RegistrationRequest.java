package com.project.mediahub.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
