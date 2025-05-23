package com.advprog.perbaikiinaja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.advprog.perbaikiinaja.model.Admin;
import com.advprog.perbaikiinaja.model.User;
import com.advprog.perbaikiinaja.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class PerbaikiinAjaApplication {
    @Autowired
    private UserRepository userRepository;
    
    @Value("${app.init-admin:true}")
    private boolean initAdmin;
    
    public static void main(String[] args) {
        SpringApplication.run(PerbaikiinAjaApplication.class, args);
    }

    @PostConstruct
    public void initAdmins() {
        if (initAdmin) {
            User admin1 = new Admin("f47ac10b-58cc-4372-a567-0e02b2c3d479", "Admin 1", "admin1@example.com", "passwordadmin1", "08123456789");
            User admin2 = new Admin("9c858901-8a57-4791-81fe-4c455b099bc9", "Admin 2", "admin2@example.com", "passwordadmin2", "08198765432");

            userRepository.save(admin1);
            userRepository.save(admin2);
        }
    }
}
