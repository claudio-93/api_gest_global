package com.gestao.mimoso.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "user")
@Data @NoArgsConstructor @AllArgsConstructor
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //@Column(name = "id")
    private Long id;

    private String name;

    private String username;

    private String password;

    //@Temporal(TemporalType.DATE)

    private LocalDate registrarData;
    /*
    //@Column(nullable = false, unique = false, length = 255)
    @OneToMany(fetch = LAZY, cascade = CascadeType.REMOVE, targetEntity = User.class, orphanRemoval = true)
    @JoinTable(name="venda", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> roles = new ArrayList<>();


    @OneToMany(cascade=CascadeType.REMOVE,
            orphanRemoval=true)
    private Set<User> albums = new HashSet<>();
    */
    @ManyToMany(fetch = EAGER, cascade=CascadeType.REMOVE)
    private Collection<Role> roles = new ArrayList<>();
}
