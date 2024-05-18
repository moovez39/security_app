package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "user")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Size(min = 5, max = 32, message = "username should be from 5 to 32 symbols")
    private String username;

    @Column
    @Size(min = 6, max = 64, message = "Password should contains at least 8 characters")
    private String password;

    @Column(nullable = false)
    private char sex;

    @Column(unique = true)
    @Email(message = "Email is invalid")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;


    public User() {
    }

    public User(String username, String password, char sex, String email) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRole(Role role) {
        if (roles == null) {
            roles = new HashSet<Role>();
        } else {
            roles.add(role);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
