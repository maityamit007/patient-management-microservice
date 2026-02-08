package com.pm.api_gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomLoggingGatewayFilterFactory
        extends AbstractGatewayFilterFactory<CustomLoggingGatewayFilterFactory.Config> {

    public CustomLoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // Allows you to define values in the YAML as a comma-separated list
        return Arrays.asList("baseMessage", "preLogger", "postLogger");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // --- Pre-filter logic ---
            if (config.isPreLogger()) {
                System.out.println("Pre-filter: " + config.getBaseMessage());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // --- Post-filter logic ---
                if (config.isPostLogger()) {
                    System.out.println("Post-filter: Response processed.");
                }
            }));
        };
    }

    // Configuration class for your filter parameters
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        // Getters and Setters
        public String getBaseMessage() { return baseMessage; }
        public void setBaseMessage(String baseMessage) { this.baseMessage = baseMessage; }
        public boolean isPreLogger() { return preLogger; }
        public void setPreLogger(boolean preLogger) { this.preLogger = preLogger; }
        public boolean isPostLogger() { return postLogger; }
        public void setPostLogger(boolean postLogger) { this.postLogger = postLogger; }
    }
}
