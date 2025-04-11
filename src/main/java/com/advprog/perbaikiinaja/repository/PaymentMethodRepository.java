package com.advprog.perbaikiinaja.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
                .userId(paymentMethod.getUserId())
                .accountNumber(paymentMethod.getAccountNumber())
                .isActive(paymentMethod.isActive())
                .build();
        }
        paymentMethods.put(paymentMethod.getId(), paymentMethod);
        return paymentMethod;
    }

    public List<PaymentMethod> findAll() {
        return new ArrayList<>(paymentMethods.values());
    }

    public Optional<PaymentMethod> findById(String id) {
        return Optional.ofNullable(paymentMethods.get(id));
    }

    public void deleteById(String id) {
        paymentMethods.remove(id);
    }

    public boolean existsById(String id) {
        return paymentMethods.containsKey(id);
    }

    public List<PaymentMethod> findByUserId(String userId) {
        return paymentMethods.values().stream()
            .filter(paymentMethod -> paymentMethod.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}