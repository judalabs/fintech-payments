package com.financial.fintechorg.dto;

import com.financial.fintechorg.domain.user.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;
    private String document;
    private String firstName;
    private String lastName;
    private UserType userType;
}
