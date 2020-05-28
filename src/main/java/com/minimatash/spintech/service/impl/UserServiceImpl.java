package com.minimatash.spintech.service.impl;

import com.minimatash.spintech.entity.User;
import com.minimatash.spintech.repository.UserRepository;
import com.minimatash.spintech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return null;
        }
        user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        return userRepository.save(user);
    }

    @Override
    public User findUser(String email, String password) {
        User foundUser = userRepository.findByEmail(email);
        if (Objects.nonNull(foundUser)) {
            if (passwordEncoder.matches(password, foundUser.getPassword())) {
                return foundUser;
            }
        }
        return null;
    }
}