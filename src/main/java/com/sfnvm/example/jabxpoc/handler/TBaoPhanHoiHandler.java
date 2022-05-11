package com.sfnvm.example.jabxpoc.handler;

import com.sfnvm.example.jabxpoc.util.JAXBUtil;
import com.sfnvm.example.jabxpoc.xml.TBaoPhanHoiXml;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TBaoPhanHoiHandler extends GenericHandler {
    private final Marshaller tBaoPhanHoiXmlMarshaller;
    private final Unmarshaller tBaoPhanHoiXmlUnmarshaller;

    public String toXml(TBaoPhanHoiXml object) throws Exception {
        try {
            return toXml(tBaoPhanHoiXmlMarshaller, object);
        } catch (Exception ex) {
            return JAXBUtil.objectToXml(object, TBaoPhanHoiXml.class);
        }
    }

    public TBaoPhanHoiXml fromXml(String xml) throws Exception {
        try {
            return fromXml(tBaoPhanHoiXmlUnmarshaller, xml, TBaoPhanHoiXml.class);
        } catch (Exception ex) {
            return JAXBUtil.xmlToObject(xml, TBaoPhanHoiXml.class);
        }
    }
}
