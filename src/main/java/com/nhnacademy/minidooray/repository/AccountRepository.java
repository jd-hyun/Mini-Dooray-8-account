package com.nhnacademy.minidooray.repository;

import com.nhnacademy.minidooray.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdAndPassword(Long accountId, String password);
}
