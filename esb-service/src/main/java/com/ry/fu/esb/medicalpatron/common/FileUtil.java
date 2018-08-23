package com.ry.fu.esb.medicalpatron.common;

import com.tt.fastdfs.FastdfsFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 16:17
 * @description Fastdfs文件上传下载等功能
 */
public class FileUtil {
	
	private static Logger log;

    /**
     *
     * @param files 文件
     * @return List<String>（filepaths:文件上传路径列表）
     */
    public static List<String> fileUpload(MultipartFile[] files){
        List<String> filePaths = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {
                try {
                    byte[] buf = files[i].getBytes();
                    String fileType = files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf(".") + 1);
                    String fileName = files[i].getOriginalFilename();
                    Long fileSize = (long)files[i].getBytes().length;
//                    String fastDFSOpen = PlatformProperties.getInstance()
//                            .getProperty("fastdfs.open");
                    String fastDFSOpen = "true";
                    //上传到fastdfs上的路径
                    String filePath = null;
                    if (StringUtils.isNotBlank(fastDFSOpen)
                            && "true".equalsIgnoreCase(fastDFSOpen)) {
                        // 启用了fastDFS，将图片上传到FastDFS服务器
                        FastdfsFacade facade = new FastdfsFacade();
                        filePath = facade.uploadFile(buf, fileName, fileType);
                        filePaths.add(filePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return filePaths;
    }


    /**
     *
     * @param filePath  文件的上传路径
     * @param fileType 文件上传方式（1:本地上传，0:fastdfs上传）
     * @return
     * @throws Exception
     */
    public static InputStream downFile(String filePath,String fileType) throws IOException {
        InputStream is = null;
        if ("1".equals(fileType)){
            is = FileUtils.openInputStream(new File(filePath));
        } else if ("0".equals(fileType)){
            //文件在fastdfs上
            String server = FastdfsProperties.getInstance().getProperty("tracker_server");
            String port = FastdfsProperties.getInstance().getProperty("http.tracker_http_port");
            URL url = new URL("http://"+server.split(":")[0]+":"+port+filePath);
            URLConnection connection = url.openConnection();
            is = connection.getInputStream();
        }
        return is;
    }
    
    /**
     *
     * @param filePath  文件路径
     * @param fileName 文件名
     * @param data base64内容
     * @return Map
     * @throws Exception
     */
	@SuppressWarnings("finally")
	public static String uploadImgForBase64(String filePath, String fileName,byte[] data) throws Exception {
        FileUtils.writeByteArrayToFile(new File(filePath + "/" + fileName), data);
        FastdfsFacade facade = new FastdfsFacade();
        String fastPath = facade.uploadFile(new File(filePath + "/" + fileName));
        new File(filePath + "/" + fileName).delete();
		return fastPath;
	}
	
	/**
	 * 
	 * @param file  待压缩文件
	 * @param parentPath 文件路径
	 * @param zos 压缩流
	 */
	private static void writeZip(File file, String parentPath, ZipOutputStream zos) {  
        if(file.exists()){  
            if(file.isDirectory()){//处理文件夹  
                parentPath+=file.getName()+File.separator;  
                File [] files=file.listFiles();  
                if(files.length != 0)  
                {  
                    for(File f:files){  
                        writeZip(f, parentPath, zos);  
                    }  
                }  
                else  
                {       //空目录则创建当前目录  
                        try {  
                            zos.putNextEntry(new ZipEntry(parentPath));  
                        } catch (IOException e) {  
                            e.printStackTrace();  
                        }  
                }  
            }else{  
                FileInputStream fis=null;  
                try {  
                    fis=new FileInputStream(file);  
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());  
                    zos.putNextEntry(ze);  
                    byte [] content=new byte[1024];  
                    int len;  
                    while((len=fis.read(content))!=-1){  
                        zos.write(content,0,len);  
                        zos.flush();  
                    }  
  
                } catch (FileNotFoundException e) { 
                	e.printStackTrace();
                } catch (IOException e) {  
                	e.printStackTrace();
                }finally{  
                    try {  
                        if(fis!=null){  
                            fis.close();  
                        }  
                    }catch(IOException e){  
                    }  
                }  
            }  
        }  
    }   
	
	/** 
     * 创建ZIP文件 
     * @param sourcePath 文件或文件夹路径 
     * @param zipPath 生成的zip文件存在路径（包括文件名） 
     */  
    public static void createZip(String sourcePath, String zipPath) {  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        try {  
            fos = new FileOutputStream(zipPath);  
            zos = new ZipOutputStream(fos);  
            writeZip(new File(sourcePath), "", zos);  
        } catch (FileNotFoundException e) {  
             log.error("创建ZIP文件失败",e);  
        } finally {  
            try {  
                if (zos != null) {  
                    zos.close();  
                }  
            } catch (IOException e) {  
                log.error("创建ZIP文件失败",e);  
            }  
  
        }  
    }
}
