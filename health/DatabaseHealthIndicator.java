package com.advprog.perbaikiinaja.health;

import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try {
            dataSource.getConnection().close();
            return Health.up()
                    .withDetail("database", "Available")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "Not Available")
                    .withException(e)
                    .build();
        }
    }
}