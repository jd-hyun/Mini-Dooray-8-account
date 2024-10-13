package com.nhnacademy.minidooray.controller;

import com.nhnacademy.minidooray.DTO.*;
import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.entity.Status;
import com.nhnacademy.minidooray.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false) // Spring Security 필터 비활성화
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAccounts1() throws Exception {
        // Mock 데이터 설정
        AccountListDTO account1 = new AccountListDTO("member1 id", "example1@example.org");
        AccountListDTO account2 = new AccountListDTO("member2 id", "example2@example.org");
        List<AccountListDTO> accounts = Arrays.asList(account1, account2);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        // MockMvc로 요청 보내기
        mockMvc.perform(get("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("member1 id")))
                .andExpect(jsonPath("$[0].email", is("example1@example.org")))
                .andExpect(jsonPath("$[1].id", is("member2 id")))
                .andExpect(jsonPath("$[1].email", is("example2@example.org")));

        // 서비스 호출 검증
        verify(accountService, Mockito.times(1)).getAllAccounts();
    }

    @Test
    void testGetAllAccountsWithId() throws Exception {
        AccountListDTO account1 = new AccountListDTO("member1 id", "example1@example.org");
        AccountListDTO account2 = new AccountListDTO("member2 id", "example2@example.org");
        List<AccountListDTO> accounts = Arrays.asList(account1, account2);

        when(accountService.getAllActiveAccountsLike(anyString())).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts")
                        .param("id", "id")  // id 파라미터 추가
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("member1 id")))
                .andExpect(jsonPath("$[0].email", is("example1@example.org")))
                .andExpect(jsonPath("$[1].id", is("member2 id")))
                .andExpect(jsonPath("$[1].email", is("example2@example.org")));

        verify(accountService, Mockito.times(1)).getAllActiveAccountsLike("id");
    }

    @Test
    void testCreateAccount() throws Exception {
        AccountCreateDTO createDTO = new AccountCreateDTO("id", "BCrypt encoded string", "example@example.org");

        // POST 요청 보내기
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        // 서비스 호출 검증
        verify(accountService, Mockito.times(1)).createAccount(any(AccountCreateDTO.class));
    }

    @Test
    void testDeleteAccount() throws Exception {
        // MockMvc로 DELETE 요청 보내기
        mockMvc.perform(delete("/api/accounts/id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 서비스 호출 검증
        verify(accountService, Mockito.times(1)).deleteAccount("id");
    }

    @Test
    void testUpdateAccount() throws Exception {
        // Given
        String existingId = "oldId";
        AccountUpdateRequestDTO updateRequestDTO = new AccountUpdateRequestDTO("newId", "newPassword", "newEmail@example.org", Status.ACTIVE);
        AccountUpdateDTO expectedResponseDTO = new AccountUpdateDTO("newId", "newEmail@example.org", Status.ACTIVE);

        when(accountService.updateAccount(eq(existingId), any(AccountUpdateRequestDTO.class)))
                .thenReturn(expectedResponseDTO);

        mockMvc.perform(put("/api/accounts/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("newId"))
                .andExpect(jsonPath("$.email").value("newEmail@example.org"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void testGetAccountById() throws Exception {
        String accountId = "id123";
        AccountDetailDTO expectedResponse = new AccountDetailDTO("id123", "password123", "example@example.org");

        when(accountService.getAccountById(accountId)).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id123"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.email").value("example@example.org"));
    }

}