package com.advprog.perbaikiinaja.service;

import java.util.List;

import com.advprog.perbaikiinaja.model.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    User getRandomTeknisi();
    Integer getJumlahPesanan(User loggedUser);
}
