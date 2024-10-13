package com.nhnacademy.minidooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;

    @NotBlank
    @Setter
    private String loginId;

    @NotBlank
    @Setter
    private String password;

    @NotBlank
    @Setter
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;
}
