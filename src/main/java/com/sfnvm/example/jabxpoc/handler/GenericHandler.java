package com.sfnvm.example.jabxpoc.handler;

import com.sfnvm.example.jabxpoc.exception.JAXBValidationException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.exceptions.BeanValidationException;
import org.eclipse.persistence.exceptions.XMLMarshalException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class GenericHandler {
    protected <T> String toXml(Marshaller marshaller, T object) throws Exception {
        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);
        return sw.toString();
    }

    protected <T> T fromXml(Unmarshaller unmarshaller, String xml, Class<T> clazz) throws JAXBValidationException {
        try {
            unmarshaller.setEventHandler(event -> {
                throw new RuntimeException(event.getMessage()
                        + "\nLocation: ["
                        + event.getLocator().getLineNumber()
                        + ":"
                        + event.getLocator().getColumnNumber()
                        + "]");
            });
            return clazz.cast(unmarshaller.unmarshal(new StringReader(xml)));
        } catch (JAXBException ex) {
            log.error(ex.getMessage(), ex);
            if (ex.getLinkedException() instanceof BeanValidationException) {
                BeanValidationException e = (BeanValidationException) ex.getLinkedException();
                throw new JAXBValidationException(e.getInternalException().getMessage());
            }
            if (ex.getLinkedException() instanceof XMLMarshalException) {
                throw new JAXBValidationException(((XMLMarshalException) ex.getLinkedException()).getInternalException().getMessage());
            }
            throw new JAXBValidationException(ex.getMessage());
        }
    }
}
