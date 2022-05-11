package com.sfnvm.example.jabxpoc.handler;

import com.sfnvm.example.jabxpoc.util.JAXBUtil;
import com.sfnvm.example.jabxpoc.xml.TDiepKHLXml;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TDiepKHLHandler extends GenericHandler {
    private final Marshaller tDiepKHLXmlMarshaller;
    private final Unmarshaller tDiepKHLXmlUnmarshaller;

    public String toXml(TDiepKHLXml object) throws Exception {
        try {
            return toXml(tDiepKHLXmlMarshaller, object);
        } catch (Exception ex) {
            return JAXBUtil.objectToXml(object, TDiepKHLXml.class);
        }
    }

    public TDiepKHLXml fromXml(String xml) throws Exception {
        try {
            return fromXml(tDiepKHLXmlUnmarshaller, xml, TDiepKHLXml.class);
        } catch (Exception ex) {
            return JAXBUtil.xmlToObject(xml, TDiepKHLXml.class);
        }
    }
}
