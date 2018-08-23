package com.ry.fu.esb.common.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class XmlTransformer extends AbstractTransformer {

    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    @Override
    public <T> T readFromXML(String str) {
        return readValueFromXML(str, jaxb2Marshaller);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Object> T readFromXML(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringSource(xml));
    }

    @SuppressWarnings("unchecked")
    private <T> T readValueFromXML(String str, Jaxb2Marshaller marshaller) {
        StreamSource source = new StringSource(str);
        T value = (T) marshaller.unmarshal(source);
        logger.debug("xml unmarshal: {}", value);
        return value;
    }

    @Override
    public String writeAsXML(Object value) throws XmlMappingException,
            IOException {
        return writeValueAsXML(value, jaxb2Marshaller);
    }

    private String writeValueAsXML(Object value, Jaxb2Marshaller marshaller)
            throws XmlMappingException, IOException {
        StringResult stringResult = new StringResult();
        marshaller.marshal(value, stringResult);
        return stringResult.toString();
    }

}
