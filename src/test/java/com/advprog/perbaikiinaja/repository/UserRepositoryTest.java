package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Admin;
import com.advprog.perbaikiinaja.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UserRepositoryTest {
    private UserRepository repository;
    private User user;

    @BeforeEach
    public void setup() {
        repository = new UserRepository();
        user = new Admin("A1", "Admin", "admin@mail.com", "admin123", "081234567");
        repository.save(user);
    }

    @Test
    public void testSaveAndFindById() {
        assertEquals(user, repository.findById("A1"));
    }

    @Test
    public void testFindAll() {
        List<User> users = repository.findAll();
        assertTrue(users.contains(user));
    }

    @Test
    public void testDeleteById() {
        repository.deleteById("A1");
        assertNull(repository.findById("A1"));
    }

    @Test
    public void testFindByEmailAndPassword() {
        User found = repository.findByEmailAndPassword("admin@mail.com", "admin123");
        assertEquals(user, found);
    }
}