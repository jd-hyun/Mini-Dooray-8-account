package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    // (GET) 모든 계정을 AccountListDTO로 반환
    @GetMapping
    public List<AccountListDTO> getAllAccounts(@RequestParam(required = false) String id) { //ListDTO 바로 응답해줘도 됨
        if (id != null) {
            return accountService.getAllActiveAccountsLike(id); // ID 검색
        }
        return accountService.getAllAccounts(); // 모든 계정 조회
    }

    // (GET) ID로 단일 회원 정보 조회
    @GetMapping("/{id}")
    public AccountDetailDTO getAccountById(@PathVariable String id) { //id,password,email
        AccountDetailDTO accountDetailDTO = accountService.getAccountById(id);
        return accountDetailDTO;
    }

    // (POST) 회원 생성
    @PostMapping
    public void createAccount(@Valid @RequestBody AccountCreateDTO accountCreateDTO) { //리턴 값 없음 생성했다는 것만 알려주기
        accountService.createAccount(accountCreateDTO);
    }

    // (PUT) 회원 정보 수정
    @PutMapping("/{id}")
    public AccountUpdateDTO updateAccount(@PathVariable String id, @Valid @RequestBody AccountUpdateRequestDTO accountUpdateRequestDTO) {
        AccountUpdateDTO accountUpdateDTO = accountService.updateAccount(id, accountUpdateRequestDTO);
        return accountUpdateDTO;
    }

    // (DELETE) 회원 삭제
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id) {//리턴값 없음 완료했다는 것만 알려주기
        accountService.deleteAccount(id);
    }
}