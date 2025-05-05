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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserByEmailAndPassword() {
        String email = "user1@example.com";
        String password = "password1";
        when(userRepository.findByEmailAndPassword(email, password))
            .thenReturn(Optional.of(user1));

        User result = userService.getUserByEmailAndPassword(email, password);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        verify(userRepository).findByEmailAndPassword(email, password);
    }

    @Test
    void testGetUserByEmailAndPasswordNotFound() {
        String email = "wrong@example.com";
        String password = "wrongpass";
        when(userRepository.findByEmailAndPassword(email, password))
            .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            userService.getUserByEmailAndPassword(email, password);
        });
        verify(userRepository).findByEmailAndPassword(email, password);
    }

    @Test
    void testFindByEmail() {
        String email = "user1@example.com";
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.findByEmail(email);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void testFindByEmailNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.findByEmail(email);
        assertNull(result);
        verify(userRepository).findAll();
    }

    @Test
    void testGetRandomTeknisi() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        User result = userService.getRandomTeknisi();
        assertNotNull(result);
        assertEquals("Teknisi", result.getRole());
        verify(userRepository).findAll();
    }

    @Test
    void testGetRandomTeknisiWhenNoTeknisi() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1));

        User result = userService.getRandomTeknisi();
        assertNull(result);
        verify(userRepository).findAll();
    }

    @Test
    void testGetRandomTeknisiEmptyList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        User result = userService.getRandomTeknisi();
        assertNull(result);
        verify(userRepository).findAll();
    }
}