package com.example.repository;

import com.example.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findUserByEmailIgnoreCase(String email);

    boolean existsUsersByEmailIgnoreCase(String email );
}
