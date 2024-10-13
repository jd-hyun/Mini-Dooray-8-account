package com.nhnacademy.minidooray.jpa;

import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.Status;
import com.nhnacademy.minidooray.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        // 테스트 계정 설정
        Account activeAccount1 = new Account();
        activeAccount1.setLoginId("id1");
        activeAccount1.setPassword("password1");
        activeAccount1.setEmail("user1@example.com");
        activeAccount1.setStatus(Status.ACTIVE);

        Account activeAccount2 = new Account();
        activeAccount2.setLoginId("id2");
        activeAccount2.setPassword("password2");
        activeAccount2.setEmail("user2@example.com");
        activeAccount2.setStatus(Status.ACTIVE);

        Account inactiveAccount = new Account();
        inactiveAccount.setLoginId("id3");
        inactiveAccount.setPassword("password3");
        inactiveAccount.setEmail("user3@example.com");
        inactiveAccount.setStatus(Status.INACTIVE); //휴면

        // 테스트 계정 저장
        accountRepository.save(activeAccount1);
        accountRepository.save(activeAccount2);
        accountRepository.save(inactiveAccount);
    }

    @Test
    void testFindByStatus() {
        // Status.ACTIVE 계정만 조회
        List<Account> activeAccounts = accountRepository.findByStatus(Status.ACTIVE);

        // 검증
        assertThat(activeAccounts).hasSize(2);
        assertThat(activeAccounts).extracting(Account::getLoginId).containsExactlyInAnyOrder("id1", "id2");
    }

    @Test
    void testFindByStatusAndLoginIdLike() {
        List<Account> activeLikeAccounts = accountRepository.findByStatusAndLoginIdLike(Status.ACTIVE, "id%");

        assertThat(activeLikeAccounts).hasSize(2);
        assertThat(activeLikeAccounts).extracting(Account::getLoginId).containsExactlyInAnyOrder("id1", "id2");

        List<Account> inactiveLikeAccounts = accountRepository.findByStatusAndLoginIdLike(Status.ACTIVE, "asdf%");
        assertThat(inactiveLikeAccounts).isEmpty();
    }
}