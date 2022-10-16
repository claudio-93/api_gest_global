package com.gestao.mimoso.service;

import com.gestao.mimoso.model.Role;
import com.gestao.mimoso.model.User;
import com.gestao.mimoso.repository.RoleRepo;
import com.gestao.mimoso.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor @Transactional
public class UserServiceImplement implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        //Log.info("Saving new User{} to the database", user.getNome());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepo.findByUsername(userName);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Page<User> getUsers(Pageable pageble) {
        return userRepo.findAll(pageble);
    }

    @Override
    public boolean existsByUserName(String user) {
        return false;
    }

    @Override
    public boolean existsByRole(String user) {
        return false;
    }

    @Override
    public User getByOneUser(Long id) {
        return userRepo.getOne(id);
    }

    @Override
    public Role getByOneRole(Long id) {
        return roleRepo.getOne(id);
    }

    @Override
    public Optional<User> findByOneUserBoolean(Long id){
        return userRepo.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepo.findById(id);
        userRepo.delete(user.get());
    }
    @Override
    public void deleteAllUser() {
        userRepo.deleteAll();
    }

    @Override
    public boolean existsByUsername(String name) {
        return false;
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepo.findById(id);
        userRepo.delete(user.get());
    }

    @Override
    public void deleteByUser(User user) {
        userRepo.delete(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null){
            //log.error("User Not Found in the database");
            throw new UsernameNotFoundException("User not Found in the database");
        }
        else{

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
