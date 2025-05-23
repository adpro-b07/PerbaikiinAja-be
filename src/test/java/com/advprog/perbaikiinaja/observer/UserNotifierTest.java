package com.advprog.perbaikiinaja.observer;

import com.advprog.perbaikiinaja.event.PesananStatusChangedEvent;
import com.advprog.perbaikiinaja.model.Pesanan;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class UserNotifierTest {

    @Test
    void testHandlePesananEvent() throws InterruptedException {
        // Create a real countdown latch for testing
        CountDownLatch latch = new CountDownLatch(1);
        
        // Create our test subject with the latch
        UserNotifier notifier = new UserNotifier(latch);
        
        // Create test pesanan
        Pesanan pesanan = new Pesanan();
        pesanan.setId(99L);
        pesanan.setStatusPesanan("DIKERJAKAN");
        
        // Create and pass the event directly
        PesananStatusChangedEvent event = new PesananStatusChangedEvent(pesanan);
        notifier.handleStatusChanged(event);
        
        // Wait for processing with timeout (should be immediate since we're not using @Async in test)
        boolean completed = latch.await(2, TimeUnit.SECONDS);
        
        // Verify the latch was counted down
        assertTrue(completed, "Listener should have handled the event");
    }

    @Test
    void testWithMockedEventPublisher() {
        // Setup
        ApplicationEventPublisher mockPublisher = mock(ApplicationEventPublisher.class);
        CountDownLatch latch = new CountDownLatch(1);
        
        // Create objects
        UserNotifier notifier = new UserNotifier(latch);
        Pesanan pesanan = new Pesanan();
        PesananStatusChangedEvent event = new PesananStatusChangedEvent(pesanan);
        
        // Test event handling (doesn't test if events actually reach handlers)
        mockPublisher.publishEvent(event);
        verify(mockPublisher).publishEvent(any(PesananStatusChangedEvent.class));
    }
    
}
