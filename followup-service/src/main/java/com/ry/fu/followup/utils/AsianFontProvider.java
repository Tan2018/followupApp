package com.ry.fu.followup.utils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import org.apache.log4j.Logger;

import java.io.File;

public class AsianFontProvider extends XMLWorkerFontProvider {

	private static final Logger logger = Logger.getLogger(AsianFontProvider.class);

//	private String path;
//
//	public AsianFontProvider(String path){
//		this.path=path;
//	}

	public Font getFont(final String fontname, final String encoding,
                        final boolean embedded, final float size, final int style,
                        final BaseColor color) {
		BaseFont bf = null;
		try {
		    	String sys = System.getProperty("os.name").toLowerCase();
		    	logger.info("系统："+sys);
		    	if(sys.indexOf("windows") != -1){
		    	    bf = BaseFont.createFont("C:"+ File.separator+"Windows"+ File.separator+"Fonts"+ File.separator+"msyh.ttc,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

                    File file = new File(File.separator+"usr"+ File.separator+"share"+ File.separator+"fonts"+ File.separator+"msyh.ttc");
                    logger.info(File.separator+"usr"+ File.separator+"share"+ File.separator+"fonts"+ File.separator+"msyh.ttc");
                    logger.info(file.exists());
		    	}else{
		    		File file = new File(File.separator+"usr"+ File.separator+"share"+ File.separator+"fonts"+ File.separator+"msyh.ttc");
		    	   	logger.info(File.separator+"usr"+ File.separator+"share"+ File.separator+"fonts"+ File.separator+"chinese"+ File.separator+"msyh.ttc");
		    		logger.info(file.exists());
					bf = BaseFont.createFont(File.separator+"usr"+ File.separator+"share"+ File.separator+"fonts"+ File.separator+"chinese"+ File.separator+"msyh.ttc,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		    	}
		 } catch (Exception e) {
		     e.printStackTrace();
		}
		Font font = new Font(bf, size, style, color);
		font.setColor(color);
		return font;
	}
}
