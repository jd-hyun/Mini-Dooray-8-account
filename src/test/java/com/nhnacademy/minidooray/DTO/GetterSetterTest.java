package com.nhnacademy.minidooray.DTO;

import com.nhnacademy.minidooray.DTO.request.AccountUpdateRequestDTO;
import com.nhnacademy.minidooray.entity.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetterSetterTest {

    @Test
    void testAccountUpdateRequestDTO() {
        AccountUpdateRequestDTO dto = new AccountUpdateRequestDTO("testId", "testPassword", "test@example.com", Status.ACTIVE);

        assertEquals("testId", dto.getId());
        assertEquals("testPassword", dto.getPassword());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(Status.ACTIVE, dto.getStatus());

        dto = new AccountUpdateRequestDTO();
        dto.setId("newId");
        dto.setPassword("newPassword");
        dto.setEmail("new@example.com");
        dto.setStatus(Status.INACTIVE);

        assertEquals("newId", dto.getId());
        assertEquals("newPassword", dto.getPassword());
        assertEquals("new@example.com", dto.getEmail());
        assertEquals(Status.INACTIVE, dto.getStatus());
    }

    @Test
    void testAccountCreateDTO() {
        AccountCreateDTO dto = new AccountCreateDTO("testId", "testPassword", "test@example.com");

        assertEquals("testId", dto.getId());
        assertEquals("testPassword", dto.getPassword());
        assertEquals("test@example.com", dto.getEmail());

        dto = new AccountCreateDTO();
        dto.setId("newId");
        dto.setPassword("newPassword");
        dto.setEmail("new@example.com");

        assertEquals("newId", dto.getId());
        assertEquals("newPassword", dto.getPassword());
        assertEquals("new@example.com", dto.getEmail());
    }

    @Test
    void testAccountDetailDTO() {
        AccountDetailDTO dto = new AccountDetailDTO("testId", "testPassword", "test@example.com");

        assertEquals("testId", dto.getId());
        assertEquals("testPassword", dto.getPassword());
        assertEquals("test@example.com", dto.getEmail());

        dto = new AccountDetailDTO();
        dto.setId("newId");
        dto.setPassword("newPassword");
        dto.setEmail("new@example.com");

        assertEquals("newId", dto.getId());
        assertEquals("newPassword", dto.getPassword());
        assertEquals("new@example.com", dto.getEmail());
    }

    @Test
    void testAccountListDTO() {
        AccountListDTO dto = new AccountListDTO("testId", "test@example.com");

        assertEquals("testId", dto.getId());
        assertEquals("test@example.com", dto.getEmail());

        dto = new AccountListDTO();
        dto.setId("newId");
        dto.setEmail("new@example.com");

        assertEquals("newId", dto.getId());
        assertEquals("new@example.com", dto.getEmail());
    }

    @Test
    void testAccountUpdateDTO() {
        AccountUpdateDTO dto = new AccountUpdateDTO("testId", "test@example.com", Status.ACTIVE);

        assertEquals("testId", dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(Status.ACTIVE, dto.getStatus());

        dto = new AccountUpdateDTO();
        dto.setId("newId");
        dto.setEmail("new@example.com");
        dto.setStatus(Status.INACTIVE);

        assertEquals("newId", dto.getId());
        assertEquals("new@example.com", dto.getEmail());
        assertEquals(Status.INACTIVE, dto.getStatus());
    }
}
