package com.nhnacademy.minidooray.DTO.request;

import com.nhnacademy.minidooray.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountUpdateRequestDTO {

    private String id;

    private String password;

    private String email;

    private Status status;
}
