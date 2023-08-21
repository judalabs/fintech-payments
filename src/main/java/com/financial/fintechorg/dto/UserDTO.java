package com.financial.fintechorg.dto;

import com.financial.fintechorg.domain.user.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;
    private String document;
    private String firstName;
    private String lastName;
    private UserType userType;
}
