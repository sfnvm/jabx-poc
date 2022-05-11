package com.sfnvm.example.jabxpoc.xml.pooling;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.xml.bind.Unmarshaller;

public class UnmarshallerPool extends GenericObjectPool<Unmarshaller> {
    public UnmarshallerPool(PooledObjectFactory<Unmarshaller> factory) {
        super(factory);
    }

    public UnmarshallerPool(
            PooledObjectFactory<Unmarshaller> factory,
            GenericObjectPoolConfig<Unmarshaller> config) {
        super(factory, config);
    }

    public UnmarshallerPool(
            PooledObjectFactory<Unmarshaller> factory,
            GenericObjectPoolConfig<Unmarshaller> config,
            AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
