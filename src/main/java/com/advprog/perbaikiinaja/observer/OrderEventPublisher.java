package com.advprog.perbaikiinaja.observer;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {
    private static final OrderEventPublisher INSTANCE = new OrderEventPublisher();
    private final List<OrderObserver> observers = new ArrayList<>();

    private OrderEventPublisher() {}

    public static OrderEventPublisher getInstance() {
        return INSTANCE;
    }

    public void register(OrderObserver observer) {
        observers.add(observer);
    }

    public void notify(Order order, OrderStatus status) {
        for (OrderObserver o : observers) {
            o.onStatusChange(order, status);
        }
    }
}
