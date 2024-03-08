package com.project.mediahub.model.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResetPasswordRequest {
    private String email;
    private String password;
    private String confirmPassword;
}
