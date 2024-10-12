package com.nhnacademy.minidooray.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountListDTO { //응답에는 필요없음

    private String id;

    private String email;
}
