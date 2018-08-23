package com.ry.fu.esb.medicaljournal.util;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


/**
 * 加签与验签工具类
 * @Project: cashier_desk
 * @Package: com.sunshine.api.util
 * @ClassName: SignatureUtil
 * @Description: <p></p>
 * @JDK version used:
 * @Author: 申姜
 * @Create Date: 2016年12月29日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SignatureCashUtils {
    private static Logger logger = LoggerFactory.getLogger(SignatureUtils.class);
    /**
     * 公/私钥生成算法:RSA,强制要求RSA密钥的长度至少为2048,因SHA256WithRSA签名算法强制要求密钥长度至少为2048
     */
    public static final String SIGN_TYPE_RSA = "RSA";
    /**
     * 密钥的长度
     */
    public static final int KEY_LENGTH = 2048;
    /**
     * 数据签名算法:SHA256WithRSA
     */
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    /**
     * RSA最大加密明文大小
     */
    public static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    public static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "publicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "privateKey";
    /**
     * UTF-8字符集
     */
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 签名字符串标识字符
     */
    public static final String PARAM_SIGN = "sign";

    /**
     * 消息加签
     * @param paramsMap
     * @return
     */
    public static String rsa256Sign(Map<String, String> paramsMap) {
        //支付平台公钥字符串不参与加签,只作为进行消息加签的钥匙
        String ygkzPrivateKey = paramsMap.get("ygkzPrivateKey");
        paramsMap.remove("ygkzPrivateKey");
        if (StringUtils.isBlank(ygkzPrivateKey)) {
            return null;
        }
        //排序
        String sortContent = getSortContent(paramsMap);
        //加签
        String sign = SignatureUtils.rsa256Sign(sortContent, ygkzPrivateKey);
        return sign;

    }

    /**
     * 将集合内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串
     * @param paramsMap
     * @return
     */
    public static String getSortContent(Map<String, String> paramsMap) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(paramsMap.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = paramsMap.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                content.append( ( index == 0 ? "" : "&" ) + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * 生成密钥对(公钥和私钥)
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(SIGN_TYPE_RSA);
        keyPairGen.initialize(KEY_LENGTH);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put(PUBLIC_KEY, publicKey);
        map.put(PRIVATE_KEY, privateKey);
        return map;
    }

    /**
     * 获取私钥
     * @param map
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> map) throws Exception {
        Key key = (Key) map.get(PRIVATE_KEY);
        return new String(Base64Utils.encode(key.getEncoded()));
    }

    /**
     * 获取公钥
     * @param map
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> map) throws Exception {
        Key key = (Key) map.get(PUBLIC_KEY);
        return new String(Base64Utils.encode(key.getEncoded()));
    }

    /**
     * SHA256WithRSA 验签
     * @param content
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean rsa256CheckContent(String content, String sign, String publicKey) throws Exception {
        try {
            logger.info("验签字符串:{}", content);
            //获取公钥对象
            byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            //SHA256WithRSA 验签
            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(CHARSET_UTF8));
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            logger.error("rsa256CheckContent failure!RSAcontent:{},sign:{}", content, sign);
            e.printStackTrace();
            return false;

        }
    }

    /**
     * SHA256WithRSA 加签
     * @param content
     * @param privateKey
     * @return
     */
    public static String rsa256Sign(String content, String privateKey) {
        try {
            //获取私钥对象
            byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            //SHA256WithRSA 加签
            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(CHARSET_UTF8));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            logger.error("rsa256Sign failure!RSAcontent:{},sign:{}", content, e.getStackTrace());
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param dataMap
     * @return
     */
    public static String buildSortJson(Map<String, String> dataMap) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(dataMap.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = dataMap.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                content.append( ( index == 0 ? "" : "&" ) + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = String.valueOf(sArray.get(key));
            if (value == null || value.equals("") || PARAM_SIGN.equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
//        String privateKey =
//        		"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMl4bnfEYbofcThxsYNe7NSIQ7aXspuaTyAZ6fJ7smM/98liOlr3sO01JoSKXAhEFoU5Wqxf73bioltT4IVp6fKFXgkZS1FhbdnafSNvLj2WM6Dc3WIEVKQyTomACWCvvHGz2mYyXZbL1PhGxltp3S13/7OOtwjYvPdtywUDpXwDAgMBAAECgYBumtIyqDpqntZegXRNxfBI4aIi/PcOG9HysqdP+v69JCIZifa1eCPGphZGT1slIpQmgY048tYZM2SktNCfDb1j8Bx+uzjpsCjvm5qmmpF0AakKc89oKzUC1RfPccdieuOPcBbmIYzG2Gpuq5s+KAplP1LdPNrDYvDeW+N5GeTPKQJBAPV9rBHw6BIkVPLNVPDQeT1+WWZ6i4N+4ihLSJX7MhYmJuHohsUIBNtd4wRTpYlAdolQbAZS6x3EleXvhWHAdtcCQQDSGFccX8qIOeceyRXt5ckMhH8BV03g9f1k1Z6caGMxqxv0UPWlFT7LzixZKvDEtZSTI5E4bgTUPBZx773WqXq1AkEAyI9Bku7ESn4QQMVsDcnkI08kbsrTTDzPO6g1xwKQKvREc+/qmvIwNaAApjpIVVerAAgKGSiJGarP5CjJSdFZxQJAIcdqEhTQsnBk2Y1JKlO8rHeNGAEac2X5lMHH26vo7u5s8y4lIKK20vvWI4wE5Aux1KpuSd1eh2UV0sHSCvZ61QJBAOLtkkzWM8fGBOKbrS1JsNjWVtUgkar+2/Vr3UQAe4ccbJSm7ib3QPFf75kGoQkN63nUFpVECKJFNQEy2Ihwsgg=";
//        String publicKey =
//        		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9iOvbpg3nxAYkh00sbYRVMvCY7d0U/1XelDeBpqFC43DWKtPMW5NrkkbBHXnyDydHpiV5xeMZu3RoGEWiOSkdIelgmgWDDn+p1FUiGU54xUntNS4V0e1nFXzrddWtrPVLFQ5B3PpM3jhkcB0FDABeXTzubSNPwWdhsoQBVNOoSnJVeB8Aot3z17IOat+x/51rtzDKO3rLC3Y90E4Lvz0DXrEw8Mjc3mPYD5zd3+d/k0KDgRQMxQEGL/LQoP7H6EBuRxHloJfE2M7O1LP8AI9XeKhd9r7k6y5zC5GetrART0buzfPlKrC00PjafLzBRTa45tE9l5OsbZ5tw+kXytqwIDAQAB";
//
//        Map<String, String> requestMap = new HashMap<String, String>();
//        requestMap.put("timestamp", "2018-03-22 10:22:25");
//        requestMap.put("returnCode", "SUCCESS");
//        requestMap.put("returnMsg", "请求成功");
//        requestMap.put("appId", "sun7b8f2b1fcdeb4ca");
//        requestMap.put("cashierUrl", "http://cashiertest.968309.net/trade/cashierCenter");
//        requestMap.put("cashierId", "6de485e00c044e5194df9fad80e9e646");
//        requestMap.put("signMode", "RSA");
//        requestMap.put("nonceStr", "77200526dd4e484ea6997960876a9555");
//        requestMap.put("merchantNo", "9683099884166530");
//
//        String content = SignatureUtils.getSortContent(requestMap);
//
//        String encryptStr = rsa256Sign(content, privateKey);
//        System.out.println("encryptStr:" + encryptStr);
//        System.out.println("content:"+content);
//
//        String password = buildSortJson(requestMap);
//        System.out.println(rsa256CheckContent(password, encryptStr, publicKey));


        String publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9iOvbpg3nxAYkh00sbYRVMvCY7d0U/1XelDeBpqFC43DWKtPMW5NrkkbBHXnyDydHpiV5xeMZu3RoGEWiOSkdIelgmgWDDn+p1FUiGU54xUntNS4V0e1nFXzrddWtrPVLFQ5B3PpM3jhkcB0FDABeXTzubSNPwWdhsoQBVNOoSnJVeB8Aot3z17IOat+x/51rtzDKO3rLC3Y90E4Lvz0DXrEw8Mjc3mPYD5zd3+d/k0KDgRQMxQEGL/LQoP7H6EBuRxHloJfE2M7O1LP8AI9XeKhd9r7k6y5zC5GetrART0buzfPlKrC00PjafLzBRTa45tE9l5OsbZ5tw+kXytqwIDAQAB";
        String str =
                "{\"timeStamp\":\"2018-03-22 10:22:25\",\"returnCode\":\"SUCCESS\",\"returnMsg\":\"请求成功\",\"appId\":\"sun7b8f2b1fcdeb4ca\",\"sign\":\"b7fhVQysVWx1B4qGCRs20c+1gG+vAXGb1ZpW9yQF5Ft3vR18UDI3T2Yq13HzjavgEEAQfDLhF1f6t2MsRlRqOKckQHGnqY8IGzIu/jDautePUE/D4p4fCQrPNKfpq+Fik8LtDWqVWkzlOfMGhzLTIaZ+WMQnFvMuKS88CeiIMa9F32KL1gHGvtCH0ByOZ33thdNVq98baXwJ1s6IZZX4TrpMajJOjuYu/RHKVfugwD0QnhKDvHvZ18WcBMGbAZ0utoRzaR9oawxMV79I3BgSCTTJOogqbGUzFVB7LpVH820GzG0GgoKZH0oTHtIt6TxRbqjtY1hukw9aa4cpu0AlSw==\",\"cashierUrl\":\"http://cashiertest.968309.net/trade/cashierCenter\",\"cashierId\":\"6de485e00c044e5194df9fad80e9e646\",\"signMode\":\"RSA\",\"nonceStr\":\"77200526dd4e484ea6997960876a9555\",\"merchantNo\":\"9683099884166530\"}";
        Map<String, String> dataMap = (Map<String, String>) JSONUtils.parse(str);
        String encryptStr = dataMap.remove("sign");
        String password = buildSortJson(dataMap);
        System.out.println(rsa256CheckContent(password, encryptStr, publicKey));

    }

}
