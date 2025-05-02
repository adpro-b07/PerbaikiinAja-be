package com.advprog.perbaikiinaja.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.advprog.perbaikiinaja.model.PaymentMethod;

@Repository
public class PaymentMethodRepository {
    private final Map<String, PaymentMethod> paymentMethods = new ConcurrentHashMap<>();

    public PaymentMethod save(PaymentMethod paymentMethod) {
        if (paymentMethod.getId() == null) {
            paymentMethod = PaymentMethod.builder()
                .id(UUID.randomUUID().toString())
                .name(paymentMethod.getName())
                .build();
        }
        paymentMethods.put(paymentMethod.getId(), paymentMethod);
        return paymentMethod;
    }

    public List<PaymentMethod> findAll() {
        return new ArrayList<>(paymentMethods.values());
    }

    public PaymentMethod findById(String id) {
        return paymentMethods.get(id);
    }

    public void deleteByName(String Name) {
        paymentMethods.values().removeIf(paymentMethod -> paymentMethod.getName().equals(Name));
    }

    public boolean existsByName(String name) {
        return paymentMethods.values().stream()
                .anyMatch(paymentMethod -> paymentMethod.getName().equals(name));
    }
}