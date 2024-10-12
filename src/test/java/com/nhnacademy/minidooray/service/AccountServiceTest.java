package com.nhnacademy.minidooray.service;

import com.nhnacademy.minidooray.DTO.AccountCreateDTO;
import com.nhnacademy.minidooray.DTO.AccountDetailDTO;
import com.nhnacademy.minidooray.DTO.AccountListDTO;
import com.nhnacademy.minidooray.DTO.AccountUpdateDTO;
import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.Status;
import com.nhnacademy.minidooray.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testGetAccountById() {
        Account account = new Account();
        account.setLoginId("id");
        account.setPassword("password");
        account.setEmail("example@example.org");
        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));

        AccountDetailDTO result = accountService.getAccountById(1L);

        assertEquals("id", result.getId()); //아이디 확인
        assertEquals("password", result.getPassword()); // 패스워드 확인
        assertEquals("example@example.org", result.getEmail()); //이메일 확인
        verify(accountRepository, times(1)).findById(1L); //1L로 저장되었는지 확인
    }

    @Test
    void testGetAllActiveAccountsLike1() {

        Account account1 = new Account();
        account1.setLoginId("member1");
        account1.setEmail("example1@example.org");
        account1.setStatus(Status.ACTIVE);

        Account account2 = new Account();
        account2.setLoginId("member2");
        account2.setEmail("example2@example.org");
        account2.setStatus(Status.ACTIVE);

        Account account3 = new Account();
        account3.setLoginId("inactiveMember");
        account3.setEmail("inactive@example.org");
        account3.setStatus(Status.INACTIVE);

        when(accountRepository.findByStatusAndLoginIdLike(eq(Status.ACTIVE), eq("%member%")))
                .thenReturn(Arrays.asList(account1, account2));

        List<AccountListDTO> result = accountService.getAllActiveAccountsLike("%member%");

        // 결과 검증: 활성 상태의 두 계정만 반환되는지 확인
        assertEquals(2, result.size());  // 리스트 크기 확인
        assertEquals("member1", result.get(0).getId());
        assertEquals("member2", result.get(1).getId());
        assertEquals("example1@example.org", result.get(0).getEmail());
        assertEquals("example2@example.org", result.get(1).getEmail());

        // 비활성화된 계정(account3)이 반환되지 않는 것을 확인
        verify(accountRepository, times(1)).findByStatusAndLoginIdLike(Status.ACTIVE, "%member%");
    }

    @Test
    void testGetAllActiveAccountsLike2() {

        Account account1 = new Account();
        account1.setLoginId("member1 id");
        account1.setEmail("example1@example.org");

        Account account2 = new Account();
        account2.setLoginId("member2 id");
        account2.setEmail("example2@example.org");

        Account account3 = new Account();
        account3.setLoginId("member3 id");
        account3.setEmail("example3@example.org");
        account3.setStatus(Status.INACTIVE);

        when(accountRepository.findByStatus(Status.ACTIVE)).thenReturn(Arrays.asList(account1, account2));

        List<AccountListDTO> result = accountService.getAllAccounts();

        assertEquals(2, result.size());  // 리스트 크기 확인
        assertEquals("member1 id", result.get(0).getId());
        assertEquals("member2 id", result.get(1).getId());
        assertEquals("example1@example.org", result.get(0).getEmail());
        assertEquals("example2@example.org", result.get(1).getEmail());

        verify(accountRepository, times(1)).findByStatus(Status.ACTIVE);
    }

    @Test
    void testCreateAccount() {
        AccountCreateDTO createDTO = new AccountCreateDTO("id", "BCrypt encoded string", "example@example.org");

        accountService.createAccount(createDTO);

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testUpdateAccount() {
        Account account = new Account();
        account.setLoginId("Id");
        account.setEmail("old@example.com");
        account.setPassword("password");
        account.setStatus(Status.INACTIVE);

        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));

        AccountUpdateRequestDTO updateDTO = new AccountUpdateRequestDTO("newId", "BCrypt encoded string", "example@example.org", Status.ACTIVE);

        AccountUpdateDTO result = accountService.updateAccount(1L, updateDTO);

        assertEquals("newId", result.getId());
        assertEquals("example@example.org", result.getEmail());
        assertEquals(Status.ACTIVE, result.getStatus());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testDeleteAccount() {
        accountService.deleteAccount(1L);

        verify(accountRepository, times(1)).deleteById(1L);
    }
}