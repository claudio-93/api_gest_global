package com.gestao.mimoso.repository;

import com.gestao.mimoso.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);
    User findByName(String name);
    User findByRegistrarData(String name);
}
