package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.entity.State;
import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountService {
//전부 DTO return 하도록 수정

    private final AccountRepository accountRepository;


    public AccountDetailDTO getAccountById(Long id) { //계정 단일 정보 조회 //이거 할때 패스워드도 받아야할거 같은데..
        Optional<Account> optionalAccount = accountRepository.findById(id);
        // Account 엔티티가 존재하는지 확인하고, 없으면 예외 처리
        Account account = optionalAccount.orElseThrow(() -> new NoSuchElementException("해당 ID의 계정을 찾을 수 없습니다."));
        // 변환된 DTO 반환
        return new AccountDetailDTO(account.getId(), account.getEmail(), account.getPassword());
    }

    public List<AccountListDTO> getAllAccounts() { //모든 계정 정보 조회 활성 상태인 애들만 부르기
        return accountRepository.findByStatus(State.ACTIVE).stream() //활성상태인 애들만 모아서 보내주기
                .map(account -> new AccountListDTO(account.getId(), account.getEmail())) // AccountListDTO로 변환
                .collect(Collectors.toList()); //리스트화
    }


    // 검색해서 멤버 찾아오기 기능 추가
    public List<AccountListDTO> getAllActiveAccountsLike(String id) {
        return accountRepository.findByStatusAndIdLike(State.ACTIVE, id).stream() //아이디가 비슷한 애들 중, 활성상태인 애들만 모아서 보내주기
                .map(account -> new AccountListDTO(account.getId(), account.getEmail())) // AccountListDTO로 변환
                .collect(Collectors.toList()); //리스트화
    }

    public void/*AccountDetailDTO*/ createAccount(AccountCreateDTO accountCreateDTO) { // 계정 생성
        Account account = new Account();
        account.setId(accountCreateDTO.getId());
        account.setPassword(accountCreateDTO.getPassword());
        account.setEmail(accountCreateDTO.getEmail());
        account.setState(State.ACTIVE); // 기본 상태 설정(활성)
        accountRepository.save(account);

        //return new AccountDetailDTO(savedAccount.getId(), savedAccount.getPassword(), savedAccount.getEmail()); (body를 줘야하는 경우)
    }

    public AccountUpdateDTO updateAccount(Long id, AccountUpdateRequestDTO accountUpdateRequestDTO) { //계정 정보 업데이트 (id,password,email,state)
        Optional<Account> optionalAccount = accountRepository.findById(id);
        Account account = optionalAccount.orElseThrow(() -> new NoSuchElementException("해당 ID의 계정을 찾을 수 없습니다."));
        account.setId(accountUpdateRequestDTO.getId());//아이디 변경
        account.setPassword(accountUpdateRequestDTO.getPassword()); //비밀번호 변경
        account.setEmail(accountUpdateRequestDTO.getEmail()); //이메일 변경
        account.setState(accountUpdateRequestDTO.getState()); //상태 변경
        accountRepository.save(account); //해당 PKId에 그대로 저장

        return new AccountUpdateDTO(account.getId(),account.getEmail(),account.getState());
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id); //삭제
    }

}
