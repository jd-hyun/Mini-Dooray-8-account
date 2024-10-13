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
        when(accountRepository.findByLoginId(any(String.class))).thenReturn(account);

        AccountDetailDTO result = accountService.getAccountById("id");

        assertEquals("id", result.getId()); //아이디 확인
        assertEquals("password", result.getPassword()); // 패스워드 확인
        assertEquals("example@example.org", result.getEmail()); //이메일 확인
        verify(accountRepository, times(1)).findByLoginId("id");
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
    void testCreateAccount1() {
        AccountCreateDTO createDTO = new AccountCreateDTO("id", "BCrypt encoded string", "example@example.org");

        accountService.createAccount(createDTO);

        verify(accountRepository, times(1)).save(any(Account.class));
    }


    @Test
    void testDeleteAccount() {
        String loginId = "id";

        accountService.deleteAccount(loginId);

        verify(accountRepository, times(1)).deleteByLoginId(loginId);
    }


    @Test
    void testCreateAccount2() {
        AccountCreateDTO accountCreateDTO = new AccountCreateDTO("id", "password1234", "example@example.org");

        when(accountRepository.existsAccountsByLoginId(accountCreateDTO.getId())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(accountCreateDTO));

        assertEquals("이미 있는 로그인 아이디입니다.", exception.getMessage());

        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void testUpdateAccount() {
        // Given
        String existingLoginId = "oldId";
        String newLoginId = "id";

        AccountUpdateRequestDTO updateRequestDTO = new AccountUpdateRequestDTO(newLoginId, "newPassword", "newEmail@example.org", Status.ACTIVE);
        Account existingAccount = new Account(1L, existingLoginId, "oldPassword", "oldEmail@example.org", Status.INACTIVE);

        when(accountRepository.findByLoginId(existingLoginId)).thenReturn(existingAccount);

        when(accountRepository.existsAccountsByLoginId(newLoginId)).thenReturn(false);

        AccountUpdateDTO updatedAccountDTO = accountService.updateAccount(existingLoginId, updateRequestDTO);

        verify(accountRepository, times(1)).save(existingAccount);

        assertEquals(newLoginId, updatedAccountDTO.getId());
        assertEquals("newEmail@example.org", updatedAccountDTO.getEmail());
        assertEquals(Status.ACTIVE, updatedAccountDTO.getStatus());
    }

    @Test
    void testUpdateAccountWithDuplicateLoginId() {
        // Given
        String existingLoginId = "oldId";
        String newLoginId = "id";

        AccountUpdateRequestDTO updateRequestDTO = new AccountUpdateRequestDTO(newLoginId, "newPassword", "newEmail@example.org", Status.ACTIVE);
        Account existingAccount = new Account(1L, existingLoginId, "oldPassword", "oldEmail@example.org", Status.INACTIVE);

        when(accountRepository.findByLoginId(existingLoginId)).thenReturn(existingAccount);

        when(accountRepository.existsAccountsByLoginId(newLoginId)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.updateAccount(existingLoginId, updateRequestDTO));

        assertEquals("이미 있는 로그인 아이디입니다.", exception.getMessage());

        verify(accountRepository, times(0)).save(any(Account.class));
    }

}