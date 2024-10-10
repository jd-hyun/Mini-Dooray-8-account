package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    // (GET) 모든 계정을 AccountListDTO로 반환
    @GetMapping
    public List<AccountListDTO> getAllAccounts() { //ListDTO 바로 응답해줘도 됨
        List<AccountListDTO> accounts = accountService.getAllAccounts();
        return accounts;
    }

    // (GET) ID로 단일 회원 정보 조회
    @GetMapping("/{id}")
    public AccountDetailDTO getAccountById(@PathVariable Long id) { //id,password,email
        AccountDetailDTO accountDetailDTO = accountService.getAccountById(id);
        return accountDetailDTO;
    }

    // (GET) ID가 비슷한 회원 정보 리스트 조회
    @GetMapping
    public List<AccountListDTO> getAllActiveAccountsLike(@RequestParam String id) { //ListDTO 바로 응답해줘도 됨
        List<AccountListDTO> accounts = accountService.getAllActiveAccountsLike(id);
        return accounts;
    }

    // (POST) 회원 생성
    @PostMapping
    public void createAccount(@Valid @RequestBody AccountCreateDTO accountCreateDTO) { //리턴 값 없음 생성했다는 것만 알려주기
        accountService.createAccount(accountCreateDTO);
    }

    // (PUT) 회원 정보 수정
    @PutMapping("/{id}")
    public AccountUpdateDTO updateAccount(@PathVariable Long id, @Valid @RequestBody AccountUpdateRequestDTO accountUpdateRequestDTO) {
        AccountUpdateDTO accountUpdateDTO = accountService.updateAccount(id, accountUpdateRequestDTO);
        return accountUpdateDTO;
    }

    // (DELETE) 회원 삭제
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {//리턴값 없음 완료했다는 것만 알려주기
        accountService.deleteAccount(id);
    }
}