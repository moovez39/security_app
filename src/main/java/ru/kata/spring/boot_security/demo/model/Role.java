package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    @NotNull
    String role_name;

//
////    @ManyToMany(cascade = CascadeType.ALL)
////    @JoinTable(name = "user_roles")
//    @Transient
//    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role(String role_name) {
        this.role_name = role_name;
    }


    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return role_name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Role() {
    }


    @Override
    public String getAuthority() {
        return role_name;
    }
}
