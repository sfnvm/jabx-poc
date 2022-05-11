package com.sfnvm.example.jabxpoc.util;

import com.sfnvm.example.jabxpoc.xml.TBaoPhanHoiXml;
import com.sfnvm.example.jabxpoc.xml.TDiepKHLXml;
import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
public class JAXBBean {

    private Marshaller initMarshaller(Marshaller marshaller) throws JAXBException {
        marshaller.setProperty(MarshallerProperties.CHARACTER_ESCAPE_HANDLER,
                (CharacterEscapeHandler) (buffer, start, length, isAttributeValue, out)
                        -> out.write(buffer, start, length));
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); // ignored append xml header
        return marshaller;
    }

    @Bean
    public Marshaller tDiepXmlMarshaller() throws JAXBException {
        Marshaller marshaller = initMarshaller(JAXBContextFactory
                .createContext(new Class[]{TDiepXml.class}, null)
                .createMarshaller());
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
        return marshaller;
    }

    @Bean
    public Unmarshaller tDiepXmlUnmarshaller() throws JAXBException {
        return JAXBContextFactory
                .createContext(new Class[]{TDiepXml.class}, null)
                .createUnmarshaller();
    }

    @Bean
    public Marshaller tDiepKHLXmlMarshaller() throws JAXBException {
        return initMarshaller(JAXBContextFactory
                .createContext(new Class[]{TDiepKHLXml.class}, null)
                .createMarshaller());
    }

    @Bean
    public Unmarshaller tDiepKHLXmlUnmarshaller() throws JAXBException {
        return JAXBContextFactory
                .createContext(new Class[]{TDiepKHLXml.class}, null)
                .createUnmarshaller();
    }

    @Bean
    public Marshaller tBaoPhanHoiXmlMarshaller() throws JAXBException {
        return initMarshaller(JAXBContextFactory
                .createContext(new Class[]{TBaoPhanHoiXml.class}, null)
                .createMarshaller());
    }

    @Bean
    public Unmarshaller tBaoPhanHoiXmlUnmarshaller() throws JAXBException {
        return JAXBContextFactory
                .createContext(new Class[]{TBaoPhanHoiXml.class}, null)
                .createUnmarshaller();
    }
}
