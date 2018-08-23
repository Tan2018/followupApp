package com.ry.fu.esb.medicalpatron.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 16:17
 * @description 获取fdfs_client.conf配置文件信息
 */
public class FastdfsProperties {
	private static Logger logger = LoggerFactory.getLogger(FastdfsProperties.class);

    private static Properties prop = null;

    private FastdfsProperties() {

    }

    public static Properties getInstance() {
        if(prop == null) {
            //读取配置文件
            prop = new Properties();
            InputStream fileInputStream = null;
            try {
                fileInputStream = FastdfsProperties.class.getResourceAsStream("/fdfs_client.conf");
                prop.load(fileInputStream);
            } catch (IOException e) {
                logger.error("读取文件出错", e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if(fileInputStream != null) fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
