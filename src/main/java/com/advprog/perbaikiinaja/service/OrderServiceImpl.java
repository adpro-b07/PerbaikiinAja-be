package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.Order;
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
}
