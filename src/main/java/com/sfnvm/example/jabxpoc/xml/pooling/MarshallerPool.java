package com.sfnvm.example.jabxpoc.xml.pooling;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.xml.bind.Marshaller;

public class MarshallerPool extends GenericObjectPool<Marshaller> {
    public MarshallerPool(PooledObjectFactory<Marshaller> factory) {
        super(factory);
    }

    public MarshallerPool(
            PooledObjectFactory<Marshaller> factory,
            GenericObjectPoolConfig<Marshaller> config) {
        super(factory, config);
    }

    public MarshallerPool(
            PooledObjectFactory<Marshaller> factory,
            GenericObjectPoolConfig<Marshaller> config,
            AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
