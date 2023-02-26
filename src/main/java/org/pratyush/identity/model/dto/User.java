package org.pratyush.identity.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String email;
    private String firstName;
    private String surname;
    private String password;
    private String dateOfBirth;
    private List<String> roles;
}
