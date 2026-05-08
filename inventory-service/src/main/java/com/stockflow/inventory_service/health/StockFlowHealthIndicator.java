package com.stockflow.inventory_service.health;

import com.stockflow.inventory_service.repository.ProductRepository;
import com.stockflow.inventory_service.repository.StockAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockFlowHealthIndicator implements HealthIndicator {

    private final ProductRepository productRepository;
    private final StockAlertRepository stockAlertRepository;

    @Override
    public Health health() {
        long totalProducts = productRepository.count();
        if (totalProducts == 0) {
            return Health.up().withDetail("message", "No hay productos en el inventario").build();
        }

        long criticalAlerts = stockAlertRepository.countBySeverity("CRITICAL");
        double criticalPercentage = (double) criticalAlerts / totalProducts * 100.0;

        if (criticalPercentage > 20.0) {
            return Health.down()
                    .withDetail("message", "Más del 20% de los productos están en alerta crítica")
                    .withDetail("criticalPercentage", String.format("%.2f%%", criticalPercentage))
                    .withDetail("totalProducts", totalProducts)
                    .withDetail("criticalAlerts", criticalAlerts)
                    .build();
        }

        return Health.up()
                .withDetail("message", "Nivel de alertas críticas dentro de los límites aceptables")
                .withDetail("criticalPercentage", String.format("%.2f%%", criticalPercentage))
                .withDetail("totalProducts", totalProducts)
                .withDetail("criticalAlerts", criticalAlerts)
                .build();
    }
}
