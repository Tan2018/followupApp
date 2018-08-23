package com.ry.fu.followup.utils;

import com.lowagie.text.pdf.BaseFont;
import com.tt.fastdfs.FastdfsFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.dom4j.*;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlToPdf {
	protected final  Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 *
	 * @param contents 记录原文
	 * @return 解析完了之后的html内容（将html转换成标准的xHtml）
	 */
	protected  static String getHtmlFile(String contents) {
		File file = null;
		try {
			long startTM=System.currentTimeMillis();

			String htmlContent = htmlParser(contents);
			System.out.print("PDF文件生成临时路径："+FileUtils.getTempDirectoryPath() + startTM + ".pdf");
			file = new File(FileUtils.getTempDirectoryPath() + File.separator + startTM + ".pdf");
			html2Pdf(htmlContent,file.getCanonicalPath());
			String fileExtName = FilenameUtils.getExtension(file.getName());
			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			FileUtils.deleteQuietly(file);
			/*FastdfsFacade fastdfsFacade = new FastdfsFacade();
			String path = fastdfsFacade.uploadFile(fileBytes, reportId, fileExtName, Collections.EMPTY_MAP);
			logger.info("(System.currentTimeMillis()-startTM = " + (System.currentTimeMillis()-startTM));
			return path;*/
		} catch (Exception e) {
			if(file!=null){
				FileUtils.deleteQuietly(file);
			}
			//logger.info("PDF文件生成异常:"+e.getMessage());
			ExceptionUtils.getStackTrace(e);
		}

		return null;

	}

	/**
	 *
	 * @param htmlContent html文件的内容
	 * @param pdfPath 生成的pdf的文件路径
	 */
	protected static void html2Pdf(String htmlContent, String pdfPath) {
		try {
			OutputStream os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);

			// 解决中文支持问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String sys = System.getProperty("os.name").toLowerCase();
			if(sys.indexOf("windows") != -1) {
				fontResolver.addFont("C:/Windows/Fonts/msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else{
				fontResolver.addFont(File.separator+"usr"+File.separator+"share"+File.separator+"fonts"+File.separator+"chinese"+File.separator+"msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}


			renderer.layout();
			renderer.createPDF(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static String htmlParser(String contents) {
		try {
			//去除html中的video标签（iText识别不了，会出错）
			String regEx = "<video.*?</video>";
			Pattern doggone = Pattern.compile(regEx);
			Matcher m = doggone.matcher(contents);
			StringBuffer newJoke = new StringBuffer();
			while(m.find()){
				m.appendReplacement(newJoke, "");
			}
			m.appendTail(newJoke);
			contents = newJoke.toString();
			org.jsoup.nodes.Document doc = Jsoup.parse(contents);
			Elements elements=doc.getElementsByClass("flatpickr-calendar");
			for (org.jsoup.nodes.Element element : elements) {
				element.remove();
			}
			contents=doc.outerHtml();
			//		String content=FileUtils.readFileToString(new File("D:\\TodayTechProject\\hip\\hip-modules\\hip-esb-service\\src\\main\\resources\\test.html"),"UTF-8");
			String text = contents.replaceAll("&nbsp;", "   ").replaceAll("<br>", "<br/>")
					.replaceAll("font-family:宋体", "font-family:Microsoft YaHei").replaceAll("&deg;","゜").replaceAll("&times;","×")
					.replaceAll("&plusmn;","±");
			//				.replaceAll(fontOld, fontNew);
			text="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" /></head><body style='font-family:Microsoft YaHei;'>"+text+"</body></html>";
			Document  document = null;
			document = DocumentHelper.parseText(text);
			Element rootElement = document.getRootElement();
			getNodes(rootElement);
			return rootElement.asXML();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static void getNodes(Element node){

		List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list

		for(Attribute attr:listAttr){//遍历当前节点的所有属性
			if(attr.getName().equals("class")&&(attr.getValue().equals("sde-value")||attr.getValue().equals("sde-value flatpickr-input"))) {
				Element parent = node.getParent();
				if (parent.getParent() != null) {
					String text=node.getText();
					parent.clearContent();
					parent.setText(text);
				}else{
					parent.addText(node.getText());
					parent.remove(node);
				}

			}else if(node.getName().equals("img")&&attr.getName().equals("src")){
				attr.setValue("http://192.168.8.111:8001/"+attr.getValue());
			}else if(node.getName().equals("td")){
				if(attr.getName().equals("class")&&attr.getValue().equals("style")){
					node.remove(attr);
				}
			}else if(node.getName().equals("table")){
				node.addAttribute("style","borderCollapse:'collapse'");
			}
		}
		for (Attribute attr : listAttr) {
			if(node.getName().equals("td")) {
				if (attr.getName().equals("class") && attr.getValue().contains("sde_table_dis")) {
					node.addAttribute("style", "border-style:''");
				}
			}
		}

		//递归遍历当前节点所有的子节点
		List<Element> listElement=node.elements();//所有一级子节点的list
		for(Element e:listElement){//遍历所有一级子节点
			getNodes(e);//递归
		}
	}

	public static String  createPdf(String contents) {

		getHtmlFile(contents);
		return null;

	}

}
