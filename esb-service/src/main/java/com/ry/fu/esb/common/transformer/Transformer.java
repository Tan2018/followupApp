package com.ry.fu.esb.common.transformer;

import org.springframework.oxm.XmlMappingException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Transformer {

    /**
     * 将XML输入流转换为对象
     *
     * @param is
     * @return
     * @throws org.springframework.oxm.XmlMappingException
     * @throws java.io.IOException
     */
    public <T> T readFromXML(InputStream is);

    /**
     * 将XML转换为对象
     *
     * @param xml
     * @return
     * @throws org.springframework.oxm.XmlMappingException
     * @throws java.io.IOException
     */
    public <T> T readFromXML(String xml);

    /**
     * 根据指定类型将XML转换为对象
     * @param xml
     * @param clazz 指定要换换的类型
     * @param <T>
     * @return
     */
    public <T> T readFromXML(String xml, Class<T> clazz) throws JAXBException;

    /**
     * 将对象转换为XML
     *
     * @param value
     * @return
     * @throws org.springframework.oxm.XmlMappingException
     * @throws java.io.IOException
     */
    public String writeAsXML(Object value) throws XmlMappingException,
            IOException;

    /**
     * 将对象转换为XML输出流
     *
     * @param os
     * @param value
     * @throws org.springframework.oxm.XmlMappingException
     * @throws java.io.IOException
     */
    public void writeAsXML(OutputStream os, Object value)
            throws XmlMappingException, IOException;

    /**
     * 将对象转换为JSON输出
     *
     * @param os
     * @param value
     * @throws Exception
     */
    public void writeAsJSON(OutputStream os, Object value)
            throws Exception;
}
