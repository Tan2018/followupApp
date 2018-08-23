package com.ry.fu.esb.common.transformer;

import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.utils.StringFormatUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

public abstract class AbstractTransformer implements Transformer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTransformer.class);

    protected Jaxb2Marshaller jaxb2Marshaller;

    public void setJaxb2Marshaller(Jaxb2Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readFromXML(InputStream inputStream) {
        Source source = getSource(inputStream);
        T result = (T) jaxb2Marshaller.unmarshal(source);
        logger.debug("inputstream unmarshal: {}", result);
        return result;
    }

    @Override
    public <T> T readFromXML(String xml) {
        throw new RuntimeException("XML read error,maybe format error");
    }

    @Override
    public String writeAsXML(Object value) throws XmlMappingException,
            IOException {
        throw new RuntimeException("write as xml error");
    }

    @Override
    public void writeAsXML(OutputStream os, Object value)
            throws XmlMappingException, IOException {
        logger.debug("outputstream marshal: {}", value);
        Result result = new StreamResult(os);
        jaxb2Marshaller.marshal(value, result);
        logWriteXML(value); // 打印返回给客户端的内容
    }

    @Override
    public void writeAsJSON(OutputStream os, Object value){
        throw new RuntimeException();
    }

    /**
     * 获取XML转换源
     *
     * @param inputStream 输入流
     * @return XML转换源
     */
    private Source getSource(InputStream inputStream) {
        Source source = null;
        try {
            String content = IOUtils.toString(inputStream, Constants.UTF_8);
            source = new StringSource(content);
            logReadXML(content);
        } catch (IOException e) {
            logger.error("Failed to read inputStream", e);
        }
        return source;
    }

    /* 打印读取日志 */
    private void logReadXML(String content) {
        content = content.replaceAll("(\\r|\\n)", "");
        logger.info("read value: \n{}", content);
    }

    /* 打印写出日志 */
    private void logWriteXML(Object value) throws IOException {
        StringWriter writer = new StringWriter();
        jaxb2Marshaller.marshal(value, new StreamResult(writer));
        String content = writer.toString();
        content = StringFormatUtils.formatXML(content);
        logger.info("write value: \n{}", content);
    }

}
