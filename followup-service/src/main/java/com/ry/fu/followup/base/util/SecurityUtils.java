package com.ry.fu.followup.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/2/6 15:55
 * @description description
 */
public class SecurityUtils {
    /**
     * 解密
     * @param cipherText 密文
     * @return 返回解密后的字符串
     * @throws Exception
     */
    public static String decryptRSA(String cipherText) throws Exception{
        // 从文件中得到私钥
        FileInputStream inPrivate = new FileInputStream(
                SecurityUtils.class.getClassLoader().getResource("").getPath() + "/pkcs8_private_key.pem");
        PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
        byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(cipherText.getBytes()), privateKey);
        String decryptStr = new String(decryptByte,"utf-8");
        return decryptStr;
    }
    /**
     * 加密
     * @param plainTest 明文
     * @return  返回加密后的密文
     * @throws Exception
     */
    public static String encryptRSA(String plainTest) throws Exception{
        FileInputStream inPublic = new FileInputStream(
                SecurityUtils.class.getClassLoader().getResource("").getPath() + "/rsa_public_key.pem");
        PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
        // 加密
        byte[] encryptByte = RSAUtils.encryptData(plainTest.getBytes(), publicKey);
        String afterencrypt = new String(Base64Utils.encode(encryptByte));
        return afterencrypt;
    }

    public static byte[] encryptAES(byte[] content, byte[] keyBytes, byte[] iv){

        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(keyBytes));
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] decryptAES(byte[] content, byte[] keyBytes, byte[] iv){

        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    private static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //生成MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = byteToHexString(md5Byte);                            // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"doctorId\" : 123,\n" +
                "    \"pageSize\" : 8,\n" +
                "    \"pageNumber\": 1,\n" +
                "    \"status\": 0\n" +
                "\n" +
                "}";
        System.out.println(json);
        json = StringUtils.replaceAll(json, " +", "").replaceAll("\\n", "");
        String sign = getMD5("todaytech"+json);
        System.out.println(json);
        System.out.println(sign);
    }

}
