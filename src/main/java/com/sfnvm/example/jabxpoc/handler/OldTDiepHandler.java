package com.sfnvm.example.jabxpoc.handler;

import com.sfnvm.example.jabxpoc.util.JAXBUtil;
import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OldTDiepHandler extends GenericHandler {
    private final Marshaller tDiepXmlMarshaller;
    private final Unmarshaller tDiepXmlUnmarshaller;

    public String toXml(TDiepXml object) throws Exception {
        try {
            return toXml(tDiepXmlMarshaller, object);
        } catch (Exception ex) {
            return JAXBUtil.objectToXml(object, TDiepXml.class);
        }
    }

    public TDiepXml fromXml(String xml) throws Exception {
        try {
            return fromXml(tDiepXmlUnmarshaller, xml, TDiepXml.class);
        } catch (Exception ex) {
            return JAXBUtil.xmlToObject(xml, TDiepXml.class);
        }
    }
}
