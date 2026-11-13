package com.ecommerce.catalogue.config;

import com.netflix.discovery.shared.transport.jersey.TransportClientFactories;
import com.netflix.discovery.shared.transport.jersey3.Jersey3TransportClientFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaTransportConfig {

    public EurekaTransportConfig() {
        System.out.println(">>> EUREKA TRANSPORT CONFIG CHARGEE <<<");
    }

    @Bean
    public TransportClientFactories<?> transportClientFactories() {
        System.out.println(">>> CREATION DU BEAN TRANSPORT EUREKA <<<");
        return new Jersey3TransportClientFactories();
    }
}
