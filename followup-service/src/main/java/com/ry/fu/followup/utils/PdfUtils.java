package com.ry.fu.followup.utils;

import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.pdf.BaseFont;
import com.tt.fastdfs.FastdfsFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gail_zhou on 2017/12/7.
 */
public class PdfUtils {

    public static final String SEPARATOR =File.separator;

    private static final Logger logger = LoggerFactory.getLogger(PdfUtils.class);

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
        Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
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

            logger.info("pdfPath=="+pdfPath);
            File file = new File(pdfPath);
            if(!file.exists()){
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(pdfPath);
            ITextRenderer renderer = new ITextRenderer();
            //renderer.setDocument(html);
            renderer.setDocumentFromString(htmlContent);

            // 解决中文支持问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            String sys = System.getProperty("os.name").toLowerCase();
            if(sys.indexOf("windows")!=-1) {
                fontResolver.addFont("C:/Windows/Fonts/msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }else{
                fontResolver.addFont(SEPARATOR+"usr"+SEPARATOR+"share"+SEPARATOR+"fonts"+SEPARATOR+"chinese"+SEPARATOR+"msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont(SEPARATOR+"usr"+SEPARATOR+"share"+SEPARATOR+"fonts"+SEPARATOR+"chinese"+SEPARATOR+"simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }

            renderer.layout();
            renderer.createPDF(os);
            os.close();
            } catch (Exception e) {
            e.printStackTrace();
            }
    }

    /**
     *
     * @param urlStr
     *            html页面的路径
     * @return 解析完了之后的html内容（将html转换成标准的xHtml）
     */
    public static String getHtmlFile(String urlStr) {
        try {
            InputStream is = new FileInputStream(new File(urlStr));

            Tidy tidy = new Tidy();

            OutputStream os2 = new ByteArrayOutputStream();
            tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
            tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
            tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
            tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>·
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
    public static String exportPdf(String pdfName, String realPath, String content, int port) {
        realPath = realPath.replaceAll("\\\\", "/");
        String outputFile = realPath + pdfName;
        //JdbcTemplate jdbcTemplate = getDefaultDao().getJdbcTemplate();
        try {
           /* String inputFile = rootPath + "/file/pdf/" + pdfName
                    + ".html";*/

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
            // FileUtils.writeStringToFile(new File(inputFile), content);
            //  String url = FileUtils.getTempDirectoryPath();;

            // 替换内容中的图片地址
            //  url = PdfUtils.replaceHtmlTag(url, "img", "src", " src=\"http://localhost:"+port+"/alo"
            //        , "\"");
            //FileUtils.writeStringToFile(new File("D:/demo.html"),url);
            // 写入pdf
           // logger.info("生成pdf文件到本地");
            PdfUtils.html2Pdf2(content, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }
    /**
     *
     * @ pdfName 要导出的PDF的名字
     * @rootPath 项目的根路径
     * @param content  html的内容
     * @port 项目端口号
     * @return
     */
    public static String  createPdf( String content,String realPath){
            long startTM=System.currentTimeMillis();
            String  pdfName=startTM+".pdf";
            content="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" /></head><body style='font-family:Microsoft YaHei;'>"+content+"</body></html>";
            String text = content.replaceAll("&nbsp;", "   ").replaceAll("<br>", "<br/>")
                    .replaceAll("font-family:宋体", "font-family:Microsoft YaHei").replaceAll("&deg;","゜").replaceAll("&times;","×")
                    .replaceAll("&plusmn;","±");
            FileUtils.getTempDirectoryPath();
            String path= exportPdf(pdfName,realPath,text,88);
            logger.info("==>本地路径:"+path);
            try {
                File file = new File(path);
                String fileExtName = FilenameUtils.getExtension(file.getName());
                byte[] fileBytes = FileUtils.readFileToByteArray(file);
                FileUtils.deleteQuietly(file);//删除本地文件
                FastdfsFacade fastdfsFacade = new FastdfsFacade();
                String pathsty = fastdfsFacade.uploadFile(fileBytes, startTM+"", fileExtName, Collections.EMPTY_MAP);
                logger.info("==>文件服务器路径:"+pathsty);
                return pathsty;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

public static  String creatHtml(String htmlContent, String realPath){
            StringBuffer html = new StringBuffer();
            html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
            html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").
            append("<head>").
            append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />").
            append("</head>").
            append("<body style='font-family:Microsoft YaHei;'>").
            append(htmlContent).
            append("</body></html>>");
            String htmlParser =null;

            long startTM=System.currentTimeMillis();
            String  htmlName=startTM+".html";

            String  htmlPath = realPath+htmlName;
            System.out.println(htmlPath);
            FileWriter fw = null;
            logger.info("htmlPath==" + htmlPath);
            File f = new File(htmlPath);

            try {
            if(!f.exists()){
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(html.toString(), 0, html.toString().length()-1);
            logger.info("---html创建成功---");
            out.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
            return htmlPath;
        }
    public static  String formPdf(String realPath,String htmlContent){

            try {

            htmlContent = htmlContent.replaceAll(";\\n",";<br/>");
            String html = creatHtml(htmlContent, realPath);
            // step 1
            String inputFile = html;
            String url = new File(inputFile).toURI().toURL().toString();

            long startTM=System.currentTimeMillis();
            String  pdfName=startTM+".pdf";
            String outputFile = realPath+pdfName;
            System.out.println(url);
            // step 2
            OutputStream os = new FileOutputStream(outputFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);

            // step 3 解决中文支持
            ITextFontResolver fontResolver = renderer
                    .getFontResolver();

            String sys = System.getProperty("os.name").toLowerCase();
            if(sys.indexOf("windows")!=-1) {
                fontResolver.addFont("C:/Windows/Fonts/msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

                fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            }else{
                fontResolver.addFont(SEPARATOR+"usr"+SEPARATOR+"share"+SEPARATOR+"fonts"+SEPARATOR+"chinese"+SEPARATOR+"msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }

            renderer.layout();
            renderer.createPDF(os);
            os.close();
            System.out.println("create pdf done!!");

            }catch (Exception e) {
            e.printStackTrace();
            }

            return null;
    }

}
