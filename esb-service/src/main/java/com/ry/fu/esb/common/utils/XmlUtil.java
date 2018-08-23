package com.ry.fu.esb.common.utils;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/7/10 16:52
 * @description Xml工具类，主要是转Json或者转Map
 */
public class XmlUtil {

    /**
     * @param xml
     * @Description xml转成JSON
     */
    public static String xml2Json(String xml) {
        String result;
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null) {
                    output.close();
                }
                if(input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                result = output.toString();
                output = null;
                input = null;
            }
        }
        return result;
    }

    public static String json2Xml(String json) {
        return jsonToXml(json, true);
    }

    /**
     * @Description json转成XML, isRemoveHea表示是否移除<?xml version="1.0" encoding="UTF-8"?>
     */
    public static String jsonToXml(String json, boolean isRemoveHead) {
        StringReader input = new StringReader(json);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isRemoveHead) {
            return output.toString().replace("\\<\\?.+\\?\\>", "");
        }
        return output.toString();
    }

    /**
     * xml转map 不带属性
     *
     * @param xmlStr
     * @param needRootKey 是否需要在返回的map里加根节点键
     * @return
     * @throws DocumentException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        Element root = doc.getRootElement();
        Map<String, Object> map = (Map<String, Object>) xml2map(root);
        if (root.elements().size() == 0 && root.attributes().size() == 0) {
            return map;
        }
        if (needRootKey) {
            //在返回的map里加根节点键（如果需要）
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put(root.getName(), map);
            return rootMap;
        }
        return map;
    }

    /**
     * xml转map 不带属性
     *
     * @param e
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Map xml2map(Element e) {
        Map map = new LinkedHashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = xml2map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        map.put(iter.getName(), m);
                    }
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!(obj instanceof List)) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj instanceof List) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        map.put(iter.getName(), iter.getText());
                    }
                }
            }
        } else {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    /**
     * xml转map 带属性
     *
     * @param xmlStr
     * @param needRootKey 是否需要在返回的map里加根节点键
     * @return
     * @throws DocumentException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map xml2MapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        Element root = doc.getRootElement();
        Map<String, Object> map = (Map<String, Object>) xml2MapWithAttr(root);
        if (root.elements().size() == 0 && root.attributes().size() == 0) {
            return map; //根节点只有一个文本内容
        }
        if (needRootKey) {
            //在返回的map里加根节点键（如果需要）
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put(root.getName(), map);
            return rootMap;
        }
        return map;
    }

    /**
     * xml转map 带属性
     *
     * @param element
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Object xml2MapWithAttr(Element element) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<Element> elements = element.elements();

        List<Attribute> listAttr = element.attributes(); // 当前节点的所有属性的list
        boolean hasAttributes = false;
        for (Attribute attr : listAttr) {
            hasAttributes = true;
            map.put("@" + attr.getName(), attr.getValue());
        }

        if (elements.size() == 0) {
            // map.put(element.getName(), element.getText());
            if (hasAttributes) {
                map.put("#text", element.getText());
            } else {
                map.put(element.getName(), element.getText());
            }

            if (!element.isRootElement()) {
                // return element.getText();
                if (!hasAttributes) {
                    return element.getText();
                }
            }
        } else if (elements.size() == 1) {
            map.put(elements.get(0).getName(),
                    xml2MapWithAttr(elements.get(0)));
        } else if (elements.size() > 1) {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Element> tempMap = new LinkedHashMap<String, Element>();
            for (Element ele : elements) {
                tempMap.put(ele.getName(), ele);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = tempMap.get(string).getNamespace();
                List<Element> elements2 = element.elements(new QName(string,
                        namespace));
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size() > 1) {
                    List<Object> list = new ArrayList<Object>();
                    for (Element ele : elements2) {
                        list.add(xml2MapWithAttr(ele));
                    }
                    map.put(string, list);
                } else {
                    // 同名的数量不大于1则直接递归去
                    map.put(string, xml2MapWithAttr(elements2.get(0)));
                }
            }
        }

        return map;
    }


    /**
     * map转xml map中没有根节点的键
     *
     * @param map
     * @param rootName
     * @throws DocumentException
     * @throws IOException
     */
    public static Document map2xml(Map<String, Object> map, String rootName) throws DocumentException, IOException {
        Document doc = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement(rootName);
        doc.add(root);
        map2xml(map, root);
        return doc;
    }

    /**
     * map转xml的Document map中含有根节点的键
     *
     * @param map
     * @throws DocumentException
     * @throws IOException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Document map2xml(Map<String, Object> map) throws DocumentException, IOException {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        if (entries.hasNext()) { //获取第一个键创建根节点
            Map.Entry<String, Object> entry = entries.next();
            Document doc = DocumentHelper.createDocument();
            Element root = DocumentHelper.createElement(entry.getKey());
            doc.add(root);
            map2xml((Map) entry.getValue(), root);
            return doc;
        }
        return null;
    }

    /**
     * map转xml的String字符串，map中含有根节点的键
     * @param map
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String map2xmlString(Map<String, Object> map) throws DocumentException, IOException {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        if (entries.hasNext()) { //获取第一个键创建根节点
            Map.Entry<String, Object> entry = entries.next();
            Document doc = DocumentHelper.createDocument();
            Element root = DocumentHelper.createElement(entry.getKey());
            doc.add(root);
            map2xml((Map) entry.getValue(), root);
            return formatXml(doc);
        }
        return null;
    }

    /**
     * map转xml
     *
     * @param map
     * @param body xml元素
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Element map2xml(Map<String, Object> map, Element body) {
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            String key = String.valueOf(entry.getKey());
            Object value = entry.getValue();
            if (key.startsWith("@")) {    //属性
                body.addAttribute(key.substring(1, key.length()), String.valueOf(value));
            } else if (key.equals("#text")) { //有属性时的文本
                body.setText(String.valueOf(value));
            } else {
                if (value instanceof List) {
                    List list = (List) value;
                    Object obj = null;
                    for (int i = 0; i < list.size(); i++) {
                        obj = list.get(i);
                        //list里是map或String，不会存在list里直接是list的，
                        if (obj instanceof Map) {
                            Element subElement = body.addElement(key);
                            map2xml((Map) list.get(i), subElement);
                        } else {
                            body.addElement(key).setText(String.valueOf(list.get(i)));
                        }
                    }
                } else if (value instanceof Map) {
                    Element subElement = body.addElement(key);
                    map2xml((Map) value, subElement);
                } else {
                    body.addElement(key).setText(String.valueOf(value));
                }
            }
        }
        return body;
    }

    /**
     * 格式化输出xml
     *
     * @param xmlStr
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String formatXml(String xmlStr) throws DocumentException, IOException {
        Document document = DocumentHelper.parseText(xmlStr);
        return formatXml(document);
    }

    /**
     * 格式化输出xml
     *
     * @param document
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String formatXml(Document document) throws DocumentException, IOException {
        // 格式化输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        //format.setEncoding("UTF-8");
        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        // 将document写入到输出流
        xmlWriter.write(document);
        xmlWriter.close();
        return writer.toString();
    }

    public static String obtainElementXml(String xml, String element) throws DocumentException {
        String formatResult = StringFormatUtils.formatXMLEscapeToCharacter(xml);
        String removeHead = formatResult.replaceAll("\\<\\?.+\\?\\>", "");
        Document document = DocumentHelper.parseText(removeHead);
        Element rootElement = document.getRootElement();
        Element res = rootElement.element(element);
        System.out.println(XmlUtil.xml2Json(res.asXML().trim()));
        return XmlUtil.xml2Json(res.asXML().trim());
    }

    public static Map obtainElementMap(String xml, String element) throws DocumentException {
        String formatResult = StringFormatUtils.formatXMLEscapeToCharacter(xml);
        String removeHead = formatResult.replaceAll("\\<\\?.+\\?\\>", "");
        removeHead = removeHead.replaceAll("(\\r|\\n)", "");
        Document document = DocumentHelper.parseText(removeHead);
        Element rootElement = document.getRootElement();
        Element res = rootElement.element(element);
        System.out.println(res.asXML());
        Map map = XmlUtil.xml2map(res.asXML(), false);
        if (map.get("res") != null) {
            return new HashMap();
        }
        return map;
    }



}
