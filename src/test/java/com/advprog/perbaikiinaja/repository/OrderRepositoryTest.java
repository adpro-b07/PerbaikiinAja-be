package com.advprog.perbaikiinaja.repository;

import com.advprog.perbaikiinaja.model.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderRepositoryTest {
    @Test
    void findByTechnicianId_shouldReturnFilteredOrders() {
        OrderRepository repo = new OrderRepository();
        Order o1 = new Order(); o1.setId("1"); o1.setTechnicianId("t1");
        Order o2 = new Order(); o2.setId("2"); o2.setTechnicianId("t1");
        Order o3 = new Order(); o3.setId("3"); o3.setTechnicianId("t2");
        repo.save(o1); repo.save(o2); repo.save(o3);

        List<Order> result = repo.findByTechnicianId("t1");

        assertEquals(2, result.size());
    }

}
