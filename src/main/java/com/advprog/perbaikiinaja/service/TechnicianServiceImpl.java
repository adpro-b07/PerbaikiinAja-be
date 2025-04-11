package com.advprog.perbaikiinaja.service;

import org.springframework.stereotype.Service;

@Service
public class TechnicianServiceImpl implements TechnicianService {
    @Override
    public boolean isTechnicianActive(String technicianId) {
        return true;
    }
}
