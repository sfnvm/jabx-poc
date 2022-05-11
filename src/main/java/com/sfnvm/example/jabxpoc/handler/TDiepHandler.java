package com.sfnvm.example.jabxpoc.handler;

import com.sfnvm.example.jabxpoc.util.JAXBUtil;
import com.sfnvm.example.jabxpoc.xml.TDiepXml;
import com.sfnvm.example.jabxpoc.xml.pooling.MarshallerPool;
import com.sfnvm.example.jabxpoc.xml.pooling.UnmarshallerPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TDiepHandler extends GenericHandler {
    private final MarshallerPool tdiepXmlMarshallerPool;
    private final UnmarshallerPool tdiepXmlUnmarshallerPool;

    public String toXml(TDiepXml object) throws Exception {
        Marshaller marshaller = tdiepXmlMarshallerPool.borrowObject();
        try {
            return toXml(marshaller, object);
        } catch (Exception ex) {
            return JAXBUtil.objectToXml(object, TDiepXml.class);
        } finally {
            tdiepXmlMarshallerPool.returnObject(marshaller);
        }
    }

    public TDiepXml fromXml(String xml) throws Exception {
        Unmarshaller unmarshaller = tdiepXmlUnmarshallerPool.borrowObject();
        try {
            return fromXml(unmarshaller, xml, TDiepXml.class);
        } catch (Exception ex) {
            return JAXBUtil.xmlToObject(xml, TDiepXml.class);
        } finally {
            tdiepXmlUnmarshallerPool.returnObject(unmarshaller);
        }
    }
}
