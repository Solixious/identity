package org.pratyush.identity.model.response;

import lombok.Data;

import java.util.List;


@Data
public class UserDetailResponse {

    private String email;
    private String firstName;
    private String surname;
    private String dateOfBirth;
    private List<String> roles;
}
