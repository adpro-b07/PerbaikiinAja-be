package com.advprog.perbaikiinaja.observer;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;

public interface OrderObserver {
    void onStatusChange(Order order, OrderStatus newStatus);
}
