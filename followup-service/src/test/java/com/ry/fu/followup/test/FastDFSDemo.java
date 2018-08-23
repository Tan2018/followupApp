package com.ry.fu.followup.test;

import com.tt.fastdfs.FastdfsFacade;
import com.tt.fastdfs.client.FileMeta;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/6/7 21:10
 * @description Fastdfs测试
 */
public class FastDFSDemo {

    public static void main(String[] args) throws Exception {
        FastdfsFacade facade = new FastdfsFacade();
        //显示服务器地址信息
        String[] serverInfos = facade.getServerInfo();
        for (String serverInfo : serverInfos) {
            System.out.println(serverInfo);
        }

        //上传文件
        String result = facade.uploadFile(new File("D:\\111.jpg"));
        System.out.println("上传文件路径：" + result);
        result = result.substring(1);
        String groupName = result.substring(0, result.indexOf("/"));
        String remoteFilename = result.substring(result.indexOf("/") + 1);
        System.out.println("groupName:" + groupName + ",remoteFilename=" + remoteFilename);

        TimeUnit.SECONDS.sleep(1);

        facade.deleteFile(groupName, remoteFilename);
    }
}
