package com.winner.trelloimplementation.user.repository;

import com.winner.trelloimplementation.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User findByEmail(String email);
}
