package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final RestTemplate restTemplate;

    // Gateway URL을 application.yml에서 가져옴
    @Value("${gateway.url}")
    private String gatewayUrl;

    public AccountController(AccountService accountService, RestTemplate restTemplate) {
        this.accountService = accountService;
        this.restTemplate = restTemplate;
    }

    // (GET) 모든 계정을 AccountListDTO로 반환
    @GetMapping
    public ResponseEntity<List<AccountListDTO>> getAllAccounts() {
        List<AccountListDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // (GET) ID로 단일 회원 정보 조회 후 Gateway로 전달
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailDTO> getAccountById(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        Optional<AccountDetailDTO> accountDetailDTO = accountService.getAccountByIdAndPassword(id, accountDTO);
        return accountDetailDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // (POST) 회원 생성 후 Gateway로 전달
    @PostMapping
    public ResponseEntity<AccountDetailDTO> createAccount(@Valid @RequestBody AccountCreateDTO accountCreateDTO) {
        AccountDetailDTO createdAccount = accountService.createAccount(accountCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    // (PUT) 회원 정보 수정 후 Gateway로 전달
    @PutMapping("/{id}")
    public ResponseEntity<AccountDetailDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountUpdateDTO accountUpdateDTO) {
        Optional<AccountDetailDTO> updatedAccount = accountService.updateAccount(id, accountUpdateDTO);
        updatedAccount.ifPresent(dto -> restTemplate.postForEntity(gatewayUrl + "/api/account", dto, String.class));
        return updatedAccount.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // (DELETE) 회원 삭제 후 Gateway로 알림 전송
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}