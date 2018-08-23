package com.ry.fu.esb.test;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/15 12:52
 * @description description
 */
public class RSATest {

    public static final String KEY_ALGORITHM="RSA";
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    private static final int KEY_SIZE=2048;
    private static final String CHARSET_UTF8 = "UTF-8";
    public static String str_pubK = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk85nGqy4uEZK5b6YERjunRZzCQV1lLtp8fBBA9Spli4nF7Rdu9105KMrqHdUk8t05c6nCFhMkWTrkghUedDmck7UA9O8ZezOXC4tEtsmG2HnsGf5cYjwwNHImo4TGqhj1uZdJxkQ82DRnwmovJ1pFVPJ9HcibkwkZT4GaJJafzdwnn3tQLPMFRkhEgQ9IGC6TtYFwn2X2rXV4oBMIX7Jv1kBxpZLuPtVyrSZMpg+EYMj256ngGob7GVFKrI8ddVXZ6avU2tYxCWgMztqILD3XodtSiKGG6zZ/xXGHZNF/2ZsACF4sKK5cr091Rjp+D/dJpA8MFFzzDyMOI7bk9/04QIDAQAB";
    public static String str_priK = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTzmcarLi4RkrlvpgRGO6dFnMJBXWUu2nx8EED1KmWLicXtF273XTkoyuod1STy3TlzqcIWEyRZOuSCFR50OZyTtQD07xl7M5cLi0S2yYbYeewZ/lxiPDA0ciajhMaqGPW5l0nGRDzYNGfCai8nWkVU8n0dyJuTCRlPgZoklp/N3Cefe1As8wVGSESBD0gYLpO1gXCfZfatdXigEwhfsm/WQHGlku4+1XKtJkymD4RgyPbnqeAahvsZUUqsjx11Vdnpq9Ta1jEJaAzO2ogsPdeh21KIoYbrNn/FcYdk0X/ZmwAIXiworlyvT3VGOn4P90mkDwwUXPMPIw4jtuT3/ThAgMBAAECggEAVCAVNOx70n5HoM3DFXAgV45weQD/dGDbIxYAmyKOR2kLXQWOmB+qqXtpnOAWZmxVo3rkPO6s3dAX+Cz/j7njf8n4CBmaUFTg+zxY8TOEU+98hD4ZjpJHnSeJytX0b6bJ9IV3HYq5yBwiEyfXP0tEhRwEhTzNfwOdb3ZHBXvPQDo0DypoJpUhXS4q80b314SwoJKVDl20JrBgyQqEhaWiIjOw9wVXSQ3uC/JnfXbnjGnP6/FAVY5J3d+uqz/kCpG1i253TzNKLZavAbYkGhjoRSXcdTesDyQQKnNjxX0ALJIGgpc1WqmH5QRmdKIiETKFan0qMNp9KRTwNVUWwKhmpQKBgQD7KCmaR76Cjpoid7oa6zoxuBuxRpBzCA9hEACWS5VOUd46gd0db6lRw3lCu2jVnRxHFm10Dkb1glfiW3+6LIR1VI7+AwiVIJ9vu3jCXiQ5E8BX1vfI9Lr92Ygjus1nM1eB4PayHN3BUw9Ues3CIWlT/NcSEnSBYdFxRzi8d4ISiwKBgQCWqAycCznLfxLMDZHFC3QgaYTuToraoVQVEozLWalh5eoMaL7gBF2PCnmVPHuFAoNllKtCUcmMr9bZISOC1tIfAQym1hvi8vHcNr7OcE1via1NR9Gc6R8JnHIQU5KgPjXqunQGmpXSp4Z3VEOE4TtIHVTZVobj/hAzoJxZ1UcfwwKBgBq4+jw1F/sSKyoxrPaCQq3ZlfC1vlbO67v7FOTo49jycv+so5cnM0/EyZklEZFw5vpVU43+muQyWyF1dNCdPfax6YjzT612TCB6RO4BUD9Wb6eo8rJJ/79UrtyGWqZ5Wv0xr23iDmIscTwe2Nen8GoEQ+njVswRWVSBLHM5D/jjAoGAYxJFoIP9UKXS3Z76wgJDLmFVikTaKOW4XlchgUQIEBnabKihBL26LKttwsbQT2qUr8MxT95q1Pp5JbYWHR9ZdAwDnt7j3b13cSILHcRaF8QI0+4rmKzwvShFFhlvLbNbT22niQQ1DB8gXO5rrEFU85peRsIQ2HMlgl3XE3geWnMCgYEAqviR2tSLxFtgh1nuSgsiFexV+6WwQcMl0Ogc1LwI/nu2y7Bo/HxThrjdekmxQZmguVZmS/N980q7rEbTyNSqGDKfVRUi7KicvCb1BOKwRA2njJcSIeeFBD/IR2tgjhmnl+WCr6Yk25phYbXB/WlgOvvgAAdNAQbY9zC/F3SpTUk=";
    /**
     * 得到公钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    /**
     * 得到私钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //***************************签名和验证*******************************
    public static byte[] sign(byte[] data) throws Exception{
        PrivateKey priK = getPrivateKey(str_priK);
        Signature sig = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        sig.initSign(priK);
        sig.update(data);
        return sig.sign();
    }

    public static boolean verify(byte[] data,byte[] sign) throws Exception{
        PublicKey pubK = getPublicKey(str_pubK);
        Signature sig = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(sign);
    }

    //************************加密解密**************************
    public static byte[] encrypt(byte[] bt_plaintext)throws Exception{
        PublicKey publicKey = getPublicKey(str_pubK);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bt_encrypted = cipher.doFinal(bt_plaintext);
        return bt_encrypted;
    }

    public static byte[] decrypt(byte[] bt_encrypted)throws Exception{
        PrivateKey privateKey = getPrivateKey(str_priK);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bt_original = cipher.doFinal(bt_encrypted);
        return bt_original;
    }
    //********************main函数：加密解密和签名验证*********************
    public static void main(String[] args) throws Exception {
        String str_plaintext = "这是一段用来测试密钥转换的明文";
        System.err.println("明文："+str_plaintext);
        byte[] bt_cipher = encrypt(str_plaintext.getBytes());
        System.out.println("加密后："+ Base64.encodeBase64String(bt_cipher));

        byte[] bt_original = decrypt(bt_cipher);
        String str_original = new String(bt_original);
        System.out.println("解密结果:"+str_original);

        String str="被签名的内容";
        System.err.println("\n原文:"+str);
        byte[] signature=sign(str.getBytes());
        System.out.println("产生签名："+Base64.encodeBase64String(signature));
        boolean status=verify(str.getBytes(), signature);
        System.out.println("验证情况："+status);
    }

}
