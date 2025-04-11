package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    @Mock
    OrderRepository orderRepo;
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void getOrdersForTechnician_shouldCallRepositoryAndReturnList() {
        Order o1 = new Order(); o1.setTechnicianId("t1");
        when(orderRepo.findByTechnicianId("t1")).thenReturn(List.of(o1));

        List<Order> result = orderService.getOrdersForTechnician("t1");

        assertEquals(1, result.size());
        assertEquals("t1", result.get(0).getTechnicianId());
    }

}
