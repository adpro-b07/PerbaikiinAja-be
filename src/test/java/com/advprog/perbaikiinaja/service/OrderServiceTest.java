package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    OrderRepository orderRepo;
    @InjectMocks
    OrderServiceImpl orderService;
    @Mock TechnicianService technicianService;

    @BeforeEach
    void setUp() {
        orderRepo = mock(OrderRepository.class);
        technicianService = mock(TechnicianService.class);
        orderService = new OrderServiceImpl(orderRepo, technicianService);
    }

    @Test
    void getOrdersForTechnician_shouldThrowIfInactive() {
        when(technicianService.isTechnicianActive("t1")).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            orderService.getOrdersForTechnician("t1");
        });
    }

    @Test
    void updateEstimation_shouldUpdateFieldsAndNotifyObserver() {
        Order order = new Order();
        order.setId("123");
        order.setStatus(OrderStatus.MENUNGGU_ESTIMASI);

        when(orderRepo.findById("123")).thenReturn(Optional.of(order));

        orderService.updateEstimation("123", 3, 150000L);

        assertEquals(3, order.getEstimatedHours());
        assertEquals(150000L, order.getEstimatedPrice());
        assertEquals(OrderStatus.MENUNGGU_KONFIRMASI_PENGGUNA, order.getStatus());
        verify(orderRepo).save(order);
    }

    @Test
    void getOrdersForTechnician_shouldReturnCorrectOrders() {
        Order order1 = new Order(); order1.setTechnicianId("tech123");
        Order order2 = new Order(); order2.setTechnicianId("tech123");

        when(technicianService.isTechnicianActive("tech123")).thenReturn(true);
        when(orderRepo.findByTechnicianId("tech123")).thenReturn(List.of(order1, order2));

        List<Order> result = orderService.getOrdersForTechnician("tech123");

        assertEquals(2, result.size());
        assertEquals("tech123", result.get(0).getTechnicianId());
    }
}
