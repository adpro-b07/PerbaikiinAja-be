package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.User;

public interface UserService {
    Iterable<User> getAllUsers();
    User getUserByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    User getRandomTeknisi();
}
