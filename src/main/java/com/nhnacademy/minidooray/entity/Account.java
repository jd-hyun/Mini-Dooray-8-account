package com.nhnacademy.minidooray.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Account { //@Columns로 이름 지정해주기

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")  // 테이블의 user_key 컬럼과 매핑
    private long accountId;

    @NotBlank
    @Setter
    @Column(name = "user_id")  // 테이블의 user_id 컬럼과 매핑
    private String id;

    @NotBlank
    @Setter
    @Column(name = "user_password")  // 테이블의 user_password 컬럼과 매핑
    private String password;

    @NotBlank
    @Setter
    @Column(name = "user_email")  // 테이블의 user_email 컬럼과 매핑
    private String email;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")  // 테이블의 user_status 컬럼과 매핑
    private State state;
}
