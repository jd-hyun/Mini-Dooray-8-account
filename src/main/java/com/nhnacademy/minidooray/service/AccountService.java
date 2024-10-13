package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.entity.Status;
import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.repository.AccountRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
//전부 DTO return 하도록 수정

    private final AccountRepository accountRepository;


    public AccountDetailDTO getAccountById(String id) { //계정 단일 정보 조회
        Account account = accountRepository.findByLoginId(id);
        // 변환된 DTO 반환
        return new AccountDetailDTO(account.getLoginId(),account.getPassword(),account.getEmail());
    }

    public List<AccountListDTO> getAllAccounts() { //모든 계정 정보 조회 활성 상태인 애들만 부르기
        return accountRepository.findByStatus(Status.ACTIVE).stream() //활성상태인 애들만 모아서 보내주기
                .map(account -> new AccountListDTO(account.getLoginId(), account.getEmail())) // AccountListDTO로 변환
                .collect(Collectors.toList()); //리스트화
    }


    // 검색해서 멤버 찾아오기 기능 추가
    public List<AccountListDTO> getAllActiveAccountsLike(String id) {
        return accountRepository.findByStatusAndLoginIdLike(Status.ACTIVE, id).stream() //아이디가 비슷한 애들 중, 활성상태인 애들만 모아서 보내주기
                .map(account -> new AccountListDTO(account.getLoginId(), account.getEmail())) // AccountListDTO로 변환
                .collect(Collectors.toList()); //리스트화
    }

    @Transactional
    public void createAccount(AccountCreateDTO accountCreateDTO) { // 계정 생성
        Account account = new Account();
        account.setLoginId(accountCreateDTO.getId());
        account.setPassword(accountCreateDTO.getPassword());
        account.setEmail(accountCreateDTO.getEmail());
        account.setStatus(Status.ACTIVE); // 기본 상태 설정(활성)
        if (accountRepository.existsAccountsByLoginId(account.getLoginId())) {
            throw new IllegalArgumentException("이미 있는 로그인 아이디입니다.");
        }
        accountRepository.save(account);

    }

    @Transactional
    public AccountUpdateDTO updateAccount(String id, AccountUpdateRequestDTO accountUpdateRequestDTO) { //계정 정보 업데이트 (id,password,email,state)
        Account account = accountRepository.findByLoginId(id);
        if (accountRepository.existsAccountsByLoginId(account.getLoginId())) {
            throw new IllegalArgumentException("이미 있는 로그인 아이디입니다.");
        }
        account.setLoginId(accountUpdateRequestDTO.getId());//아이디 변경
        account.setPassword(accountUpdateRequestDTO.getPassword()); //비밀번호 변경
        account.setEmail(accountUpdateRequestDTO.getEmail()); //이메일 변경
        account.setStatus(accountUpdateRequestDTO.getStatus()); //상태 변경
        accountRepository.save(account); //해당 PKId에 그대로 저장

        return new AccountUpdateDTO(account.getLoginId(),account.getEmail(),account.getStatus());
    }

    @Transactional
    public void deleteAccount(String id) {
        accountRepository.deleteByLoginId(id); //삭제
    }

}
