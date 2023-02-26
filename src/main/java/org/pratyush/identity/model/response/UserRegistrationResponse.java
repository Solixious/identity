package org.pratyush.identity.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegistrationResponse {
    private Boolean successful;
}
