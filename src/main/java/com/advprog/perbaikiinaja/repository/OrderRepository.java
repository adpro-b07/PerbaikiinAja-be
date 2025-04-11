package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<Order> findByTechnicianId(String technicianId) {
        return orders.values().stream()
                .filter(o -> technicianId.equals(o.getTechnicianId()))
                .toList();
    }

    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public void deleteById(String id) {
        orders.remove(id);
    }
}

