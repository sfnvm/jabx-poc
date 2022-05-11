package com.sfnvm.example.jabxpoc.util;

import com.sfnvm.example.jabxpoc.exception.JAXBValidationException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.exceptions.BeanValidationException;
import org.eclipse.persistence.exceptions.XMLMarshalException;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.CharacterEscapeHandler;
import org.javatuples.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.UUID;

@Slf4j
public class JAXBUtil {
    private JAXBUtil() {
    }

    public static <T> String objectToXml(T object, Class<T> clazz) throws JAXBException {
        return objectToXml(object, clazz, false);
    }

    public static <T> String objectToXml(T object, Class<T> clazz, boolean appendHeader)
    throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{clazz}, null);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(MarshallerProperties.CHARACTER_ESCAPE_HANDLER,
                (CharacterEscapeHandler) (buffer, start, length, isAttributeValue, out)
                        -> out.write(buffer, start, length));
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        if (!appendHeader) {
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        }
        jaxbMarshaller.marshal(object, sw);
        return sw.toString();
    }

    public static <T> T xmlToObject(File xmlFile, Class<T> clazz)
    throws JAXBException {
        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{clazz}, null);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Object data = jaxbUnmarshaller.unmarshal(xmlFile);
        if (!clazz.isInstance(data)) {
            throw new ClassCastException("Invalid xml data");
        }
        return clazz.cast(data);
    }

    public static <T> T xmlToObject(String xmlString, Class<T> clazz) throws JAXBValidationException {
        try {
            JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{clazz}, null);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            // unmarshaller.setEventHandler(event -> {
            //     String errorMsg = event.getMessage();
            //     if (errorMsg.contains("unexpected element")) {
            //         log.warn("Cảnh báo XML chứa các thẻ không có trong chuẩn: {}", errorMsg);
            //         return true;
            //     }
            //     throw new RuntimeException(errorMsg + "\nLocation: [" + event.getLocator().getLineNumber() + ":" + event.getLocator().getColumnNumber() + "]");
            // });
            return clazz.cast(unmarshaller.unmarshal(new StringReader(xmlString)));

        } catch (JAXBException ex) {
            log.error(ex.getMessage(), ex);
            if (ex.getLinkedException() instanceof BeanValidationException) {
                BeanValidationException e = (BeanValidationException) ex.getLinkedException();
                throw new JAXBValidationException(new JAXBXPath().getXPathFromErr(e.getInternalException().getMessage(), clazz));
            }
            if (ex.getLinkedException() instanceof XMLMarshalException) {
                throw new JAXBValidationException(((XMLMarshalException) ex.getLinkedException()).getInternalException().getMessage());
            }
            throw new JAXBValidationException(ex.getMessage());
        }
    }

    public static <T> T validateXml(String xmlString, String xsdFileName, Class<T> clazz)
    throws JAXBException, SAXException {
        if (!xsdFileName.endsWith(".xsd")) {
            throw new JAXBException("Wrong xsd format");
        }

        URL xsdUrl = JAXBUtil.class.getClassLoader().getResource("xsd/" + xsdFileName);
        if (xsdUrl == null) {
            throw new JAXBException("Xsd file not found");
        }

        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{clazz}, null);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(xsdUrl);
        jaxbUnmarshaller.setSchema(schema);

        Object data = jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
        return clazz.cast(data);
    }

    public static Document loadDocumentFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    /**
     * [REF](https://stackoverflow.com/a/10356325/9501437)
     * # Keywords: transform org.w3c.com document to string
     */
    public static String documentToString(Document document)
    throws TransformerException {
        StringWriter writer = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        return writer.toString();
    }

    /**
     * [REF](https://stackoverflow.com/a/14315225/9501437)
     * # Keyword: convert node to document java org.w3.dom
     */
    public static Document loadNodeToDocument(Node node) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document newDocument = builder.newDocument();
        Node importedNode = newDocument.importNode(node, true);
        newDocument.appendChild(importedNode);

        return newDocument;
    }

    public static Pair<String, UUID> generateStringFormatUUID() {
        UUID randomId = UUID.randomUUID();
        return new Pair<>("Id-" + randomId.toString().replace("-", ""), randomId);
    }

    public static Pair<String, UUID> generateStringFormatUUID(UUID uuid) {
        return new Pair<>("Id-" + uuid.toString().replace("-", ""), uuid);
    }

    /**
     * Credit: https://stackoverflow.com/a/18987428
     */
    public static UUID getIdFromStringFormat(String uuidWithPrefix) {
        String uuid = uuidWithPrefix.substring(3).replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5");
        return UUID.fromString(uuid);
    }
}
