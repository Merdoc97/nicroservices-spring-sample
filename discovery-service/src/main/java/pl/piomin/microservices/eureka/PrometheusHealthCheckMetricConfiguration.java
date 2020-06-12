package pl.piomin.microservices.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusHealthCheckMetricConfiguration {
    @Autowired
    private HealthEndpoint healthEndpoint;

    @Bean
    MeterRegistryCustomizer prometheusHealthCheck() {
        return registry -> registry.gauge("health", healthEndpoint, this::healthToCode);
    }


    private int healthToCode(HealthEndpoint ep) {
        Status status = ep.health().getStatus();
        switch (status.toString()) {
            case "UP":
                return 1;
            case "UNKNOWN":
                return 2;
            case "OUT_OF_SERVICE":
                return 3;
            default:
                return 0;
        }
    }

}
