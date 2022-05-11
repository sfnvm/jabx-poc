package com.sfnvm.example.jabxpoc.handler;

import com.sfnvm.example.jabxpoc.util.JAXBUtil;
import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Service
public class IadTDiepHandler extends GenericHandler {
    private Marshaller initMarshaller(Marshaller marshaller) throws JAXBException {
        marshaller.setProperty(MarshallerProperties.CHARACTER_ESCAPE_HANDLER,
                (CharacterEscapeHandler) (buffer, start, length, isAttributeValue, out)
                        -> out.write(buffer, start, length));
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); // ignored append xml header
        return marshaller;
    }

    public String toXml(TDiepXml object) throws Exception {
        Marshaller marshaller = initMarshaller(JAXBContextFactory
                .createContext(new Class[]{TDiepXml.class}, null)
                .createMarshaller());
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
        try {
            return toXml(initMarshaller(marshaller), object);
        } catch (Exception ex) {
            return JAXBUtil.objectToXml(object, TDiepXml.class);
        }
    }

    public TDiepXml fromXml(String xml) throws Exception {
        try {
            return fromXml(JAXBContextFactory
                    .createContext(new Class[]{TDiepXml.class}, null)
                    .createUnmarshaller(), xml, TDiepXml.class);
        } catch (Exception ex) {
            return JAXBUtil.xmlToObject(xml, TDiepXml.class);
        }
    }
}
