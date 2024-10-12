package com.nhnacademy.minidooray.repository;

import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByStatus(Status status);
    List<Account> findByStatusAndLoginIdLike(Status status, String id);
}
