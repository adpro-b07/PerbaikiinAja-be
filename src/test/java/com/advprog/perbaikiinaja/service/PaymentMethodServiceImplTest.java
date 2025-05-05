package com.advprog.perbaikiinaja.service;

import com.advprog.perbaikiinaja.model.PaymentMethod;
import com.advprog.perbaikiinaja.repository.PaymentMethodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentMethodServiceImplTest {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

@Test
public void testCreatePaymentMethodSuccess() {
    when(paymentMethodRepository.existsByName("OVO")).thenReturn(false);
    when(paymentMethodRepository.save(any())).thenAnswer(invocation -> {
        PaymentMethod savedMethod = (PaymentMethod) invocation.getArgument(0);
        savedMethod.setId(1L);
        return savedMethod;
    });

    PaymentMethod method = paymentMethodService.createPaymentMethod("OVO");

    assertNotNull(method);
    assertNotNull(method.getId());
    assertEquals("OVO", method.getName());
    verify(paymentMethodRepository).save(any());
}

    @Test
    public void testCreatePaymentMethodDuplicate() {
        when(paymentMethodRepository.existsByName("OVO")).thenReturn(true);

        assertThrows(RuntimeException.class, () ->
                paymentMethodService.createPaymentMethod("OVO"));
    }


    @Test
    public void testGetPaymentMethodByName() {
        PaymentMethod method = new PaymentMethod("Dana");
        when(paymentMethodRepository.findAll()).thenReturn(List.of(method));

        PaymentMethod found = paymentMethodService.getPaymentMethodByName("Dana");
        assertNotNull(found);
        assertEquals("Dana", found.getName());
    }

    @Test
    public void testGetPaymentMethodByNameNotFound() {
        when(paymentMethodRepository.findAll()).thenReturn(List.of());

        PaymentMethod found = paymentMethodService.getPaymentMethodByName("Nonexistent");
        assertNull(found);
    }

    @Test
    public void testUpdatePaymentMethodSuccess() {
        PaymentMethod oldMethod = new PaymentMethod("LinkAja");
        when(paymentMethodRepository.findAll()).thenReturn(List.of(oldMethod));
        when(paymentMethodRepository.existsByName("LinkAja")).thenReturn(true);
        when(paymentMethodRepository.existsByName("ShopeePay")).thenReturn(false);
        when(paymentMethodRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        PaymentMethod updated = paymentMethodService.updatePaymentMethod("LinkAja", "ShopeePay");

        assertEquals("ShopeePay", updated.getName());
    }

    @Test
    public void testUpdatePaymentMethodTargetExists() {
        PaymentMethod method = new PaymentMethod("LinkAja");
        when(paymentMethodRepository.findAll()).thenReturn(List.of(method));
        when(paymentMethodRepository.existsByName("ShopeePay")).thenReturn(true);

        assertThrows(RuntimeException.class, () ->
                paymentMethodService.updatePaymentMethod("LinkAja", "ShopeePay"));
    }

    @Test
    public void testUpdatePaymentMethodNotFound() {
        when(paymentMethodRepository.findAll()).thenReturn(List.of());

        assertThrows(RuntimeException.class, () ->
                paymentMethodService.updatePaymentMethod("BCA", "BNI"));
    }

    @Test
    public void testDeletePaymentMethodSuccess() {
        when(paymentMethodRepository.existsByName("Mandiri")).thenReturn(true);

        paymentMethodService.deletePaymentMethod("Mandiri");
        verify(paymentMethodRepository).deleteByName("Mandiri");
    }

    @Test
    public void testDeletePaymentMethodNotFound() {
        when(paymentMethodRepository.existsByName("Unknown")).thenReturn(false);

        assertThrows(RuntimeException.class, () ->
                paymentMethodService.deletePaymentMethod("Unknown"));
    }

    @Test
    public void testGetAllPaymentMethods() {
        when(paymentMethodRepository.findAll()).thenReturn(List.of(
                new PaymentMethod("A"), new PaymentMethod("B")
        ));

        Iterable<PaymentMethod> all = paymentMethodService.getAllPaymentMethods();
        assertNotNull(all);
        assertEquals(2, ((List<PaymentMethod>) all).size());
    }
}
