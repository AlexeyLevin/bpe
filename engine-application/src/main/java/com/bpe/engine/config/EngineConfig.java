package com.bpe.engine.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(value = {
//        OpenApiConfiguration.class,
//        RestEndpointConfiguration.class,
//        GatewayConfiguration.class,
        UsecaseConfiguration.class,
        SchedulingConfiguration.class,
        ExecutorConfiguration.class,
        PersistenceConfiguration.class
})
@EnableAsync
@EnableScheduling
@EnableAutoConfiguration
public class EngineConfig {
}
