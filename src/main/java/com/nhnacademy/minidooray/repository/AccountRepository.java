package com.nhnacademy.minidooray.repository;

import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByLoginId(String id);
    List<Account> findByStatus(Status status);
    List<Account> findByStatusAndLoginIdLike(Status status, String id);
    boolean existsAccountsByLoginId(String id);
    void deleteByLoginId(String id);
}
