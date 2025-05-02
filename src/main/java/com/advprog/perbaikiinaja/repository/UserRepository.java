package com.advprog.perbaikiinaja.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.advprog.perbaikiinaja.model.User;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User findById(String id) {
        return users.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void deleteById(String id) {
        users.remove(id);
    }

    public User findByEmailAndPassword(String email, String password) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
