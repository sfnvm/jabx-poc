package com.sfnvm.example.jabxpoc.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "debug.pool")
@Component
public class PoolConfig extends GenericObjectPoolConfig {
}
