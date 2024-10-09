package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.entity.Status;
import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountService {


    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<AccountDetailDTO> getAccountByIdAndPassword(Long accountId, AccountDTO accountDTO) {
        return accountRepository.findByIdAndPassword(accountId, accountDTO.getPassword())
                .map(account -> new AccountDetailDTO(account.getId(), account.getPassword(), account.getEmail()));
    }

    public List<AccountListDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountListDTO(account.getId(), account.getEmail()))
                .collect(Collectors.toList());
    }

    public AccountDetailDTO createAccount(AccountCreateDTO accountCreateDTO) {
        Account account = new Account();
        account.setId(accountCreateDTO.getId());
        account.setPassword(accountCreateDTO.getPassword());
        account.setEmail(accountCreateDTO.getEmail());
        account.setStatus(Status.ACTIVE); // 기본 상태 설정
        Account savedAccount = accountRepository.save(account);

        return new AccountDetailDTO(savedAccount.getId(), savedAccount.getPassword(), savedAccount.getEmail());
    }

    public Optional<AccountDetailDTO> updateAccount(Long id, AccountUpdateDTO accountUpdateDTO) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setEmail(accountUpdateDTO.getEmail()); // 이메일 업데이트
                    account.setId(accountUpdateDTO.getId()); // ID 업데이트
                    Account updatedAccount = accountRepository.save(account);//업데이트 저장
                    return new AccountDetailDTO(updatedAccount.getId(), updatedAccount.getPassword(), updatedAccount.getEmail());
                });
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id); //삭제
    }

}
