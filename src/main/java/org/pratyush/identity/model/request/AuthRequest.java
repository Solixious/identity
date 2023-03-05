package org.pratyush.identity.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String refreshToken;
}
