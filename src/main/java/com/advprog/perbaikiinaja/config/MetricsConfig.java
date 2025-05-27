package com.advprog.perbaikiinaja.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter pesananCreatedCounter(MeterRegistry meterRegistry) {
        return Counter.builder("pesanan_created_total")
                .description("Total number of pesanan created")
                .register(meterRegistry);
    }

    @Bean
    public Counter kuponUsedCounter(MeterRegistry meterRegistry) {
        return Counter.builder("kupon_used_total")
                .description("Total number of kupon used")
                .register(meterRegistry);
    }

    @Bean
    public Timer paymentProcessingTime(MeterRegistry meterRegistry) {
        return Timer.builder("payment_processing_duration")
                .description("Time taken to process payments")
                .register(meterRegistry);
    }
}