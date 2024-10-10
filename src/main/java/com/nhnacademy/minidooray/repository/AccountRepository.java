package com.nhnacademy.minidooray.repository;

import com.nhnacademy.minidooray.entity.Account;
import com.nhnacademy.minidooray.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByStatus(State status);
    List<Account> findByStatusAndIdLike(State status, String id);
}
