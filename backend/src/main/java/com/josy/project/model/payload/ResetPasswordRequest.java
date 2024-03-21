package com.josy.project.model.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResetPasswordRequest {
    private String email;
    private String password;
}
