package org.pratyush.identity.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRefreshResponse {
    private String accessToken;
}
