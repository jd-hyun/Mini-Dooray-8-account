package com.nhnacademy.minidooray.DTO;

import com.nhnacademy.minidooray.entity.Status;
import jakarta.persistence.Id;
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
public class AccountUpdateDTO {

    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;
}