package com.advprog.perbaikiinaja.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.service.PenggunaService;
import com.advprog.perbaikiinaja.service.TeknisiService;
import com.advprog.perbaikiinaja.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private TeknisiService teknisiService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<User>> getUser() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        try {
            User user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> payload) {
        try {
            User createdUser;
            if (payload.get("email") == null || payload.get("password") == null) {
                return ResponseEntity.badRequest().build();
            }
            if (payload.get("role").equalsIgnoreCase("teknisi")) {
                createdUser = teknisiService.createTeknisi(payload.get("namaLengkap"), payload.get("email"), payload.get("password"),
                        payload.get("noTelp"), payload.get("alamat"));
            } else if (payload.get("role").equalsIgnoreCase("pengguna")) {
                createdUser = penggunaService.createPengguna(payload.get("namaLengkap"), payload.get("email"), payload.get("password"),
                        payload.get("noTelp"), payload.get("alamat"));
            } else {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> payload, HttpSession session) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            User user = userService.getUserByEmailAndPassword(email, password);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            session.setAttribute("loggedUser", user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/session")
    public ResponseEntity<User> checkSession(HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(loggedUser);
    }
}