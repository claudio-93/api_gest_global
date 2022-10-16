package com.gestao.mimoso.service;

import com.gestao.mimoso.model.Role;
import com.gestao.mimoso.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User  saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    User getUser(String username);
    Page<User> getUsers(Pageable pageble);
    boolean existsByUserName(String user);
    boolean existsByRole(String user);
    User getByOneUser(Long id);
    Role getByOneRole(Long id);
    public Optional<User> findByOneUserBoolean(Long id);
    void deleteUser(Long id);
    void deleteById(Long id);
    void deleteByUser(User user);
    public void deleteAllUser();
    boolean existsByUsername(String name);
}
