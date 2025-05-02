package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Pengguna;
import com.advprog.perbaikiinaja.model.Teknisi;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        user1 = new Pengguna(
            UUID.randomUUID().toString(),
            "User One",
            "user1@example.com",
            "password1",
            "08123456789",
            "Jalan Pengguna No. 1"
        );
        
        user2 = new Teknisi(
            UUID.randomUUID().toString(),
            "User Two",
            "user2@example.com",
            "password2",
            "08198765432",
            "Jalan Teknisi No. 2"
        );
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        Iterable<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertTrue(result instanceof List);
        assertEquals(2, ((List<User>) result).size());
    }

    @Test
    void testGetUserByEmailAndPassword() {
        String email = "user1@example.com";
        String password = "password1";
        when(userRepository.findByEmailAndPassword(email, password)).thenReturn(user1);

        User result = userService.getUserByEmailAndPassword(email, password);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
    }

    @Test
    void testFindByEmail() {
        String email = "user1@example.com";
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.findByEmail(email);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testFindByEmailNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.findByEmail(email);
        assertNull(result);
    }

    @Test
    void testGetRandomTeknisi() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.getRandomTeknisi();
        assertNotNull(result);
        assertTrue("TEKNISI".equalsIgnoreCase(result.getRole()));
    }

    @Test
    void testGetRandomTeknisiWhenNoTeknisi() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1));

        User result = userService.getRandomTeknisi();
        assertNull(result);
    }

    @Test
    void testGetRandomTeknisiEmptyList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        User result = userService.getRandomTeknisi();
        assertNull(result);
    }
}
