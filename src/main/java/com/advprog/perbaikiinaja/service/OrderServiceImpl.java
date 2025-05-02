package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
import com.advprog.perbaikiinaja.model.OrderStatus;
import com.advprog.perbaikiinaja.observer.OrderEventPublisher;
import com.advprog.perbaikiinaja.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final TechnicianService technicianService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, TechnicianService technicianService) {
        this.orderRepo = orderRepo;
        this.technicianService = technicianService;
    }

    @Override
    public List<Order> getOrdersForTechnician(String technicianId) {
        if (!technicianService.isTechnicianActive(technicianId)) {
            throw new IllegalStateException("Technician is not active");
        }
        return orderRepo.findByTechnicianId(technicianId);
    }

    @Override
    public void updateEstimation(String orderId, int hours, Long price) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setEstimatedHours(hours);
        order.setEstimatedPrice(price);
        order.setStatus(OrderStatus.MENUNGGU_KONFIRMASI_PENGGUNA);

        orderRepo.save(order);

        // panggil observer (masih hardcode)
        OrderEventPublisher.getInstance().notify(order, order.getStatus());
    }
}
