package com.ry.fu.followup.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.pdf.BaseFont;
import com.tt.fastdfs.FastdfsFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlToPdf2 {

	/**
	 *
	 * @param content html内容
	 * @param path 写入pdf的文件地址
	 */
	public static void html2Pdf(String content, String path) {
		try {

			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream(new File(path)));
			document.open();
			document.addLanguage(Locale.ENGLISH.getLanguage());
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(pdfWriter,document,new ByteArrayInputStream(content.getBytes()),null,new AsianFontProvider());
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param str html内容
	 * @param tag 需要改变的标签
	 * @param tagAttrib 需要改变的标签的属性
	 * @param startTag 开始位置
	 * @param endTag 结束位置  （replaceHtmlTag(content.toString(), "img", "src", "src=\"E:/project3/ALO/alo/src/main/webapp", "\"");）
	 * @return 改变后的html内容
	 */
    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*" ;
        String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"" ;
        Pattern patternForTag = Pattern.compile (regxpForTag, Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib, Pattern. CASE_INSENSITIVE );
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer( "<"+tag+" ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                String attributeStr = matcherForAttrib.group(1);
                System.out.println(attributeStr);
                if(attributeStr.indexOf("http://") == -1){
                	attributeStr=attributeStr.substring(4, attributeStr.length());
                    matcherForAttrib.appendReplacement(sbreplace, startTag + attributeStr + endTag);
                }
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

	/**
	 *
	 * @param htmlContent html文件的内容
	 * @param pdfPath 生成的pdf的文件路径
	 */
	public static void html2Pdf2(String htmlContent, String pdfPath) {
		try {
//			String url = new File(htmlPath).toURI().toURL().toString();
			OutputStream os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);

			// 解决中文支持问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String sys = System.getProperty("os.name").toLowerCase();
			if(sys.indexOf("windows") != -1) {
				fontResolver.addFont("C:/Windows/Fonts/msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else{
				fontResolver.addFont(File.separator+"usr"+ File.separator+"share"+ File.separator+"fonts"+ File.separator+"chinese"+ File.separator+"msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}


			renderer.layout();
			renderer.createPDF(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
    	String content = null;
        try {
			content=FileUtils.readFileToString(new File("D:/a.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("原始字符串为:"+content);
        String newStr = replaceHtmlTag(content.toString(), "img", "src", "src=\"E:/project3/ALO/alo/src/main/webapp", "\"");
        System.out.println("转换后的:"+newStr);
    }
	public static String reportToPdf(String content, String reportId) {
		String fastPath = null;
		String outputFile = "D:/pdf_test/" + reportId + ".pdf";
		try {
			String inputFile = "D:/pdf_test/" + reportId
					+ ".html";

			//去除html中的video标签（iText识别不了，会出错）
			String regEx = "<video.*?</video>";
			Pattern doggone = Pattern.compile(regEx);
			Matcher m = doggone.matcher(content);
			StringBuffer newJoke = new StringBuffer();
			while(m.find()){
				m.appendReplacement(newJoke, "");
			}
			m.appendTail(newJoke);
			content = newJoke.toString();
			// 将报告内容写入html文件中
			FileUtils.writeStringToFile(new File(inputFile), content);
			String url = getHtmlFile(inputFile);

			// 替换内容中的图片地址
			/*url = HtmlToPdf2.replaceHtmlTag(url, "img", "src", " src=\"http://localhost:"+port+"/alo"
					, "\"");*/
			FileUtils.writeStringToFile(new File("D:/demo.html"),url);
			// 写入pdf
			HtmlToPdf2.html2Pdf2(url, outputFile);
			// 写入fastdfs
			FastdfsFacade facade = new FastdfsFacade();
			fastPath = facade.uploadFile(new File(outputFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fastPath;
	}

	public static String getHtmlFile(String urlStr) {
		try {
			InputStream is = new FileInputStream(new File(urlStr));

			Tidy tidy = new Tidy();

			OutputStream os2 = new ByteArrayOutputStream();
			tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
			tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
			tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
			tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
			tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
			tidy.parse(is, os2);

			is.close();

			// 解决乱码 --将转换后的输出流重新读取改变编码
			String temp;
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(
							((ByteArrayOutputStream) os2).toByteArray()),
					"utf-8"));
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			FileUtils.writeStringToFile(new File(urlStr), sb.toString());
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
