package com.example.repository;

import com.example.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query("""
            SELECT a
            FROM Authority a
            WHERE a.name=:nombre
            ORDER BY a.id
            LIMIT 1
            """)
    Optional<Authority> findByName(String nombre);
}
