package com.sfnvm.example.jabxpoc.xml.pooling;

import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Component
public class TdiepXmlMarshallerFactory extends BasePooledObjectFactory<Marshaller> {
    private Marshaller initMarshaller(Marshaller marshaller) throws JAXBException {
        marshaller.setProperty(
                MarshallerProperties.CHARACTER_ESCAPE_HANDLER,
                (CharacterEscapeHandler) (buffer, start, length, isAttributeValue, out)
                        -> out.write(buffer, start, length));
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        return marshaller;
    }

    @Override
    public Marshaller create() throws Exception {
        Marshaller marshaller = initMarshaller(JAXBContextFactory
                .createContext(new Class[]{TDiepXml.class}, null)
                .createMarshaller());
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
        return marshaller;
    }

    @Override
    public PooledObject<Marshaller> wrap(Marshaller obj) {
        return new DefaultPooledObject<>(obj);
    }
}
