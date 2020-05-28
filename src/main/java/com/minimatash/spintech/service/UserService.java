package com.minimatash.spintech.service;

import com.minimatash.spintech.entity.User;

public interface UserService {
    User registerUser(String email, String password);
    User findUser(String email, String password);
}

