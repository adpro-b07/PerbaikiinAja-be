package com.advprog.perbaikiinaja.controller;

import com.advprog.perbaikiinaja.command.Command;
import com.advprog.perbaikiinaja.command.CreatePaymentMethodCommand;
import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.service.PaymentMethodService;
import com.advprog.perbaikiinaja.strategy.PaymentMethodSearchByName;
import com.advprog.perbaikiinaja.strategy.SearchStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentMethodController {
    
    @Autowired
    private PaymentMethodService paymentMethodService;
    
    @GetMapping
    public ResponseEntity<Iterable<PaymentMethod>> getAllPaymentMethods() {
        try {
            Iterable<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
            return ResponseEntity.ok(paymentMethods);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<PaymentMethod> createPaymentMethod(
            @RequestBody Map<String, String> payload) {

        String name = payload.get("name");

        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Command<PaymentMethod> command = new CreatePaymentMethodCommand(paymentMethodService, name);
            PaymentMethod result = command.execute();
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/get/{name}")
    public ResponseEntity<PaymentMethod> getPaymentMethodByName(@PathVariable("name") String name) {
        try {
            SearchStrategy<PaymentMethod> strategy = new PaymentMethodSearchByName(paymentMethodService, name);
            PaymentMethod result = strategy.search();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/update/{name}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable("name") String name, @RequestBody Map<String, String> payload) {
        
        String newName = payload.get("name");
        
        if (newName == null || newName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            PaymentMethod updatedPaymentMethod = 
                paymentMethodService.updatePaymentMethod(name, newName);
            return ResponseEntity.ok(updatedPaymentMethod);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable("name") String name) {
        
        try {
            paymentMethodService.deletePaymentMethod(name);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}