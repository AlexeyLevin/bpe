package com.bpe.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import com.bpe.engine.config.EngineConfig;

@Import(EngineConfig.class)
public class EngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(EngineApplication.class, args);
    }
}
