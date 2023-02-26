package org.pratyush.identity.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserRegistrationRequest {
    private String email;
    private String firstName;
    private String surname;
    private String password;
    private String dateOfBirth;
}
