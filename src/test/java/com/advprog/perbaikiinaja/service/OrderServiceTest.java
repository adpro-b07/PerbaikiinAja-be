package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

}
