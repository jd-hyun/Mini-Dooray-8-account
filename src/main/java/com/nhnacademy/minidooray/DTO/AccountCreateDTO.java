package com.nhnacademy.minidooray.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;
}
