package org.pratyush.identity.model.request;

import lombok.Data;

@Data
public class AuthRefreshRequest {
    private String refreshToken;
}
