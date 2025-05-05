package com.advprog.perbaikiinaja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email + " and password: " + password));
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getRandomTeknisi() {
        List<User> teknisiList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole().equalsIgnoreCase("TEKNISI")) {
                teknisiList.add(user);
            }
        }
        if (teknisiList.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * teknisiList.size());
        return teknisiList.get(randomIndex);
    }
}
