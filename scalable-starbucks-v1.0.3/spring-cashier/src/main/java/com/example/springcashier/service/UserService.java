package com.example.springcashier.service;

import java.util.List;

import com.example.springcashier.login.User;

public interface UserService {
    public void saveUser(User user);

    public List<Object> isUserPresent(User user);
}