package com.ry.fu.esb.common.utils;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class StringFormatUtils {

	/**
	 * 格式化XML输出
	 * 
	 * @return
	 */
	public static String formatXML(String xml) {
		try {
			InputSource src = new InputSource(new StringReader(xml));
			Node document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(src).getDocumentElement();
			boolean keepDeclaration = xml.startsWith("<?xml");
			DOMImplementationLS impl = (DOMImplementationLS) DOMImplementationRegistry
					.newInstance().getDOMImplementation("LS");
			LSSerializer writer = impl.createLSSerializer();

			DOMConfiguration domConfig = writer.getDomConfig();
			domConfig.setParameter("format-pretty-print", true);
			domConfig.setParameter("xml-declaration", keepDeclaration);
			return writer.writeToString(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将XML字符串中的符号格式化为转义字符
	 * @param strContainEscape
	 * @return
	 */
	public static String formatXMLCharacterToEscape(String strContainEscape){
		return org.apache.commons.lang3.StringUtils.replaceEachRepeatedly(strContainEscape,new String[]{"<", ">"}, new String[]{"&lt;", "&gt;"});
	}

	/**
	 * 将XML字符串中的转义字符格式化为符号
	 * @param strContainEscape
	 * @return
	 */
	public static String formatXMLEscapeToCharacter(String strContainEscape){
		return org.apache.commons.lang3.StringUtils.replaceEachRepeatedly(strContainEscape, new String[]{"&lt;", "&gt;"}, new String[]{"<", ">"});
	}
	/**
	* @Author:Boven
	* @Description:字符串排序
	* @Date: Created in 10:17 2018/6/4
	*/
	public static String sort(String str){
		char[] s1 = str.toCharArray();
		for(int i=0;i<s1.length;i++){
			for(int j=0;j<i;j++){
				if(s1[i]<s1[j]){
					char temp = s1[i];
					s1[i] = s1[j];
					s1[j] = temp;
				}
			}
		}
		return String.valueOf(s1);
	}
}
