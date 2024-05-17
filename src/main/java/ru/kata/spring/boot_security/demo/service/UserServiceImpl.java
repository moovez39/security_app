package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        user.setUsername(user.getUsername());
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        user.setPassword(user.getPassword());
        user.setSex(user.getSex());
        user.setEmail(user.getEmail());
        userRepository.save(user);
        return true;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
