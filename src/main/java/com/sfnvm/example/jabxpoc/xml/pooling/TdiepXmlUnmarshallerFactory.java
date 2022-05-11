package com.sfnvm.example.jabxpoc.xml.pooling;

import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.Unmarshaller;

@Component
public class TdiepXmlUnmarshallerFactory extends BasePooledObjectFactory<Unmarshaller> {
    @Override
    public Unmarshaller create() throws Exception {
        return JAXBContextFactory
                .createContext(new Class[]{TDiepXml.class}, null)
                .createUnmarshaller();
    }

    @Override
    public PooledObject<Unmarshaller> wrap(Unmarshaller obj) {
        return new DefaultPooledObject<>(obj);
    }
}
