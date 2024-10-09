package com.nhnacademy.minidooray;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.Status;
import com.nhnacademy.minidooray.repository.AccountRepository;
import com.nhnacademy.minidooray.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {
        Account account1 = new Account();
        account1.setId("member1 id");
        account1.setEmail("example1@example.org");

        Account account2 = new Account();
        account2.setId("member2 id");
        account2.setEmail("example2@example.org");

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<AccountListDTO> accounts = accountService.getAllAccounts();

        assertEquals(2, accounts.size());
        assertEquals("member1 id", accounts.get(0).getId());
        assertEquals("example1@example.org", accounts.get(0).getEmail());
        assertEquals("member2 id", accounts.get(1).getId());
        assertEquals("example2@example.org", accounts.get(1).getEmail());
    }

    @Test
    public void testGetAccountByIdAndPassword() {
        Account account = new Account();
        account.setId("member1 id");
        account.setEmail("example@example.org");
        account.setPassword("password");

        when(accountRepository.findByIdAndPassword(any(Long.class), any(String.class)))
                .thenReturn(Optional.of(account));

        AccountDTO accountDTO = new AccountDTO("member1 id", "password");
        Optional<AccountDetailDTO> accountDetailDTO = accountService.getAccountByIdAndPassword(1L, accountDTO);

        assertTrue(accountDetailDTO.isPresent());
        assertEquals("member1 id", accountDetailDTO.get().getId());
        assertEquals("example@example.org", accountDetailDTO.get().getEmail());
    }

    @Test
    public void testCreateAccount() {
        AccountCreateDTO accountCreateDTO = new AccountCreateDTO("user1", "password1", "user1@example.com");
        Account account = new Account();
        account.setId(accountCreateDTO.getId());
        account.setPassword(accountCreateDTO.getPassword());
        account.setEmail(accountCreateDTO.getEmail());
        account.setStatus(Status.ACTIVE);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDetailDTO createdAccount = accountService.createAccount(accountCreateDTO);

        assertEquals("user1", createdAccount.getId());
        assertEquals("user1@example.com", createdAccount.getEmail());
    }

    @Test
    public void testUpdateAccount() {
        // Mocking existing account
        Account existingAccount = new Account();
        existingAccount.setId("user1"); // 기존 ID
        existingAccount.setEmail("oldEmail@example.com");
        existingAccount.setPassword("password"); // 패스워드도 추가

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));

        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account updatedAccount = invocation.getArgument(0);
            updatedAccount.setPassword("password"); // 비밀번호는 기존 비밀번호로 유지
            return updatedAccount; // 업데이트된 계정을 반환
        });

        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO("newUser1", "newEmail@example.com");

        Optional<AccountDetailDTO> updatedAccount = accountService.updateAccount(1L, accountUpdateDTO);

        assertTrue(updatedAccount.isPresent());
        assertEquals("newUser1", updatedAccount.get().getId()); // 업데이트된 ID 확인
        assertEquals("newEmail@example.com", updatedAccount.get().getEmail()); // 업데이트된 이메일 확인
    }

    @Test
    public void testDeleteAccount() {
        Long accountId = 1L;

        // AccountRepository의 deleteById 메소드가 호출되는지 확인
        accountService.deleteAccount(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }
}
