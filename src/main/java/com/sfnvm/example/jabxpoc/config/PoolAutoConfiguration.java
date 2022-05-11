package com.sfnvm.example.jabxpoc.config;

import com.sfnvm.example.jabxpoc.xml.pooling.MarshallerPool;
import com.sfnvm.example.jabxpoc.xml.pooling.TdiepXmlMarshallerFactory;
import com.sfnvm.example.jabxpoc.xml.pooling.TdiepXmlUnmarshallerFactory;
import com.sfnvm.example.jabxpoc.xml.pooling.UnmarshallerPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PoolAutoConfiguration {
    @Bean
    public MarshallerPool tdiepXmlMarshallerPool(
            PoolConfig poolConfig,
            TdiepXmlMarshallerFactory tdiepXmlMarshallerFactory) {
        // Deal with Spring beans
        poolConfig.setJmxEnabled(false);
        poolConfig.setMaxTotal(1000);
        return new MarshallerPool(tdiepXmlMarshallerFactory, poolConfig);
    }

    @Bean
    public UnmarshallerPool tdiepXmlUnmarshallerPool(
            PoolConfig poolConfig,
            TdiepXmlUnmarshallerFactory tdiepXmlUnmarshallerFactory) {
        // Deal with Spring beans
        poolConfig.setJmxEnabled(false);
        poolConfig.setMaxTotal(1000);
        return new UnmarshallerPool(tdiepXmlUnmarshallerFactory, poolConfig);
    }
}
