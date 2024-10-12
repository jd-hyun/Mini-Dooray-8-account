package com.nhnacademy.minidooray.DTO;

import com.nhnacademy.minidooray.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {

    private String id;

    private String email;

    private Status status;
}