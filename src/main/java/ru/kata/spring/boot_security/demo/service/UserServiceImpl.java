package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        if (!roleRepository.existsByRoleName("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (!roleRepository.existsByRoleName("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
//        User userToUpdate = userRepository.findByUsername(user.getUsername());
//        System.out.println(userToUpdate.getId());

//        if (userToUpdate != null) {
//            userRepository.s
        if (userRepository.findByUsername(user.getUsername()) != null) {
            userRepository.save(user);
        } else {
            user.setRoles(Collections.singleton(roleRepository.getById(1L)));
            userRepository.save(user);
        }
    }
//        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
//        user.setRoles(Collections.singleton(roleRepository.getById(1L)));

//        user.setUsername(user.getUsername());
//        user.setPassword(user.getPassword());
//        user.setSex(user.getSex());
//        user.setEmail(user.getEmail());
//        userRepository.save(user);
//        return true;
//    }
//    public void saveUser(User user) {
//        System.out.println(user);
//        if (!userRepository.existsById(user.getId())) {
//            userRepository.save(user);
//        } else {
//            user.setRoles(Collections.singleton(roleRepository.getById(1L)));
//            userRepository.save(user);
//        }
//    }


    public void editUser(User user, Long id) {
        System.err.println(user);
        System.err.println(user.getUsername());
        User userToEdit = userRepository.getById(id);
        System.err.println(userToEdit);
        userToEdit.setRoles(user.getRoles());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setUsername(user.getUsername());
        userToEdit.setSex(user.getSex());
        userToEdit.setPassword(user.getPassword());
        userRepository.save(userToEdit);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
