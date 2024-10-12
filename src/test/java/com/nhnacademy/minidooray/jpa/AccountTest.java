package com.nhnacademy.minidooray.jpa;

import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTest {

    @Test
    void testAllArgsConstructor() {
        Account account = new Account(1L, "loginId", "password", "email@example.com", Status.ACTIVE);

        assertEquals(1L, account.getId());
        assertEquals("loginId", account.getLoginId());
        assertEquals("password", account.getPassword());
        assertEquals("email@example.com", account.getEmail());
        assertEquals(Status.ACTIVE, account.getStatus());
    }

    @Test
    void testNoArgsConstructorAndSetter() {
        Account account = new Account();
        account.setLoginId("loginId");
        account.setPassword("password");
        account.setEmail("email@example.com");
        account.setStatus(Status.INACTIVE);

        assertNotNull(account);
        assertEquals("loginId", account.getLoginId());
        assertEquals("password", account.getPassword());
        assertEquals("email@example.com", account.getEmail());
        assertEquals(Status.INACTIVE, account.getStatus());
    }

    @Test
    void testGetterAndSetter() {
        Account account = new Account();
        account.setLoginId("loginId");
        account.setPassword("password");
        account.setEmail("email@example.com");
        account.setStatus(Status.ACTIVE);

        assertEquals("loginId", account.getLoginId());
        assertEquals("password", account.getPassword());
        assertEquals("email@example.com", account.getEmail());
        assertEquals(Status.ACTIVE, account.getStatus());
    }
}