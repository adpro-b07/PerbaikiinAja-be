package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Admin;
import com.advprog.perbaikiinaja.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    
    private User user;

    @BeforeEach
    public void setup() {
        repository.deleteAll(); // Clear database before each test
        user = new Admin("A1", "Admin", "admin@mail.com", "admin123", "081234567");
        user = repository.save(user);
    }

    @Test
    public void testSaveAndFindById() {
        Optional<User> found = repository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals(user.getEmail(), found.get().getEmail());
        assertEquals(user.getNamaLengkap(), found.get().getNamaLengkap());
    }

    @Test
    public void testFindAll() {
        List<User> users = repository.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getId().equals(user.getId())));
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(user.getId());
        Optional<User> found = repository.findById(user.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByEmailAndPassword() {
        Optional<User> found = repository.findByEmailAndPassword("admin@mail.com", "admin123");
        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
        assertEquals(user.getEmail(), found.get().getEmail());
    }

    @Test
    public void testFindByEmailAndPasswordNotFound() {
        Optional<User> found = repository.findByEmailAndPassword("wrong@mail.com", "wrongpass");
        assertFalse(found.isPresent());
    }

    @Test
    public void testCount() {
        long count = repository.count();
        assertEquals(1, count);
    }
}