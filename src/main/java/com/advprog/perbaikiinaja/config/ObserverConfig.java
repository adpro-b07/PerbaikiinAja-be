package com.advprog.perbaikiinaja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.advprog.perbaikiinaja.observer.PesananObserver;
import com.advprog.perbaikiinaja.observer.PesananPublisher;
import com.advprog.perbaikiinaja.observer.UserNotifier;

@Configuration
public class ObserverConfig {

    @Bean
    public PesananPublisher pesananPublisher() {
        PesananPublisher publisher = new PesananPublisher();
        publisher.addObserver(userNotifier());
        return publisher; 
    }
    
    @Bean
    public PesananObserver userNotifier() {
        return new UserNotifier(null);
    }
}