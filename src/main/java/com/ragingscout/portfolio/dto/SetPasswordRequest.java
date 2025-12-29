package com.ragingscout.portfolio.dto;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String username;
    private String password;
    private String email;
}

