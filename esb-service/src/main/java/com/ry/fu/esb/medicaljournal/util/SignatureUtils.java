package com.ry.fu.esb.medicaljournal.util;

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
 * @Autor : Nick
 * @Date : 2018-03-15
 */
public class SignatureUtils {

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
//    public static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
//    public static final int MAX_DECRYPT_BLOCK = 128;
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

    private static String cPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMl4bnfEYbofcThxsYNe7NSIQ7aXspuaTyAZ6fJ7smM/98liOlr3sO01JoSKXAhEFoU5Wqxf73bioltT4IVp6fKFXgkZS1FhbdnafSNvLj2WM6Dc3WIEVKQyTomACWCvvHGz2mYyXZbL1PhGxltp3S13/7OOtwjYvPdtywUDpXwDAgMBAAECgYBumtIyqDpqntZegXRNxfBI4aIi/PcOG9HysqdP+v69JCIZifa1eCPGphZGT1slIpQmgY048tYZM2SktNCfDb1j8Bx+uzjpsCjvm5qmmpF0AakKc89oKzUC1RfPccdieuOPcBbmIYzG2Gpuq5s+KAplP1LdPNrDYvDeW+N5GeTPKQJBAPV9rBHw6BIkVPLNVPDQeT1+WWZ6i4N+4ihLSJX7MhYmJuHohsUIBNtd4wRTpYlAdolQbAZS6x3EleXvhWHAdtcCQQDSGFccX8qIOeceyRXt5ckMhH8BV03g9f1k1Z6caGMxqxv0UPWlFT7LzixZKvDEtZSTI5E4bgTUPBZx773WqXq1AkEAyI9Bku7ESn4QQMVsDcnkI08kbsrTTDzPO6g1xwKQKvREc+/qmvIwNaAApjpIVVerAAgKGSiJGarP5CjJSdFZxQJAIcdqEhTQsnBk2Y1JKlO8rHeNGAEac2X5lMHH26vo7u5s8y4lIKK20vvWI4wE5Aux1KpuSd1eh2UV0sHSCvZ61QJBAOLtkkzWM8fGBOKbrS1JsNjWVtUgkar+2/Vr3UQAe4ccbJSm7ib3QPFf75kGoQkN63nUFpVECKJFNQEy2Ihwsgg=";
    private static String cPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9iOvbpg3nxAYkh00sbYRVMvCY7d0U/1XelDeBpqFC43DWKtPMW5NrkkbBHXnyDydHpiV5xeMZu3RoGEWiOSkdIelgmgWDDn+p1FUiGU54xUntNS4V0e1nFXzrddWtrPVLFQ5B3PpM3jhkcB0FDABeXTzubSNPwWdhsoQBVNOoSnJVeB8Aot3z17IOat+x/51rtzDKO3rLC3Y90E4Lvz0DXrEw8Mjc3mPYD5zd3+d/k0KDgRQMxQEGL/LQoP7H6EBuRxHloJfE2M7O1LP8AI9XeKhd9r7k6y5zC5GetrART0buzfPlKrC00PjafLzBRTa45tE9l5OsbZ5tw+kXytqwIDAQAB";

    //医保公钥(商户)
    private static String insurancePublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiogfSuor71SXVPnP33dNL08/sKETWGgG5CL0jzdxQfj6xleMt9rnguPXLDJnGUtq+oQFWMgGDDbY5txH4Cy9WuSMMLpcgbrtf2ca3H2oBuqdfhMXO+/8afMG/o5dQCbAesiNoHPqgfUoWHY9KGT1092pF0J5MdrvItugicRUKlNgTxvMAtI4rWA/xho7BgRIje8fjPayMvAJiNmP8yYVMc6lWurUHIXCag08bQ9E+QFxIBCrNIQ/4sAsCvrAjrzvW6f/w3hi/kdXlaAJ40GTgQuzoCauRDTKErKLxgifQ7sc9wZJqM1/65IWcVOnOgDFq9e//eaqwOcjkm7Xz4W+OQIDAQAB";
    //医保私钥(商户)
    private static String insurancePrivateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCKiB9K6ivvVJdU+c/fd00vTz+woRNYaAbkIvSPN3FB+PrGV4y32ueC49csMmcZS2r6hAVYyAYMNtjm3EfgLL1a5IwwulyBuu1/ZxrcfagG6p1+Exc77/xp8wb+jl1AJsB6yI2gc+qB9ShYdj0oZPXT3akXQnkx2u8i26CJxFQqU2BPG8wC0jitYD/GGjsGBEiN7x+M9rIy8AmI2Y/zJhUxzqVa6tQchcJqDTxtD0T5AXEgEKs0hD/iwCwK+sCOvO9bp//DeGL+R1eVoAnjQZOBC7OgJq5ENMoSsovGCJ9Duxz3BkmozX/rkhZxU6c6AMWr17/95qrA5yOSbtfPhb45AgMBAAECggEAKVs75FSGazuYthCiAzeNflstS2C68sCTlhXtZrgJgsV+sCnA475hMQ0t1o7xgnamaEVzhy6nx4u0My998anJmdHzHDT2mvAgKNGyDjMhL3dpLxMp84XQmByqcOI1aopnGgC3bzhLwSJcOhSKa33AedCJw1zf64dNrt1cTuSir8Ol4aGPjV2MsPFopLUumvJ60KB/Zpowo7LmPNOL+h6ZZyVE7wsaEB/WsqBkxPY2QP7cib+H+xMcQ8sm0xETWoO3a7/tRnF4RdU6a3XpSUQsSHpZsA9ijtluxLK850JDWvOTlHrc+Mps4DBvabvIR/REEdic0bqLHSouZOR41JohAQKBgQC+Ixyqj8Rjkp0o8npcDk9sz9iJmREZ7Mxhaxt6e3yxqxlTi/kmnjzrrDi05NLYc5Nlk6YedNY5tkuOCiexdcSRis+egdSNonq6EAM/aVchK4rjEbfoHjxcos1LlvCxI9IP2nBJUnfd2ek2rZ8/xAnYhgDjcoKDmijRuMXdvoFZ4QKBgQC6hMUPEQk51hsdV1zIPOw/oP9BbMptlJZiGqhJmc4O2/IR/aEgqTCv38vte46ruusMXWcB5nYYnHDrVI3WA8xj13l9eZspfREcHpEmIad8TxwPqHGlAIJlet3qXLeem1aTSxvj1/H8Nrlntg4PP3khfBWAdrnVOC5r0np2+JNfWQKBgGcTz/GjeRtmG29cbPtFAsgBsel+oufEGVTi5QW0tnf3QwapBoxqEqrNNfzTZH7VwV26+r7eJlkpO7KoTLaHGuawMzhCOEIEiLYgAjK5X/PqkCPjsijTY3PxCozlKl1OBw5zJJaLb9JE1Yq/+2ptU80fcJHdvWWtCF4R5qr9IVDhAoGAMP/qSQ//rXsK+oVV8aDXq8ue41AySeFsNG+uYE+Qq6SqjoFkEBGCoeGYUFZSX9kmIMR06DHkOoj2vUwkSuIKkvwrhYQp+BdMJGf/V9Ylium5hH+LA38Dwqcpn4UmE/vZHa7wr8WuY8T4pj9UqDrTlrFjcLFAFrzArUDJPXIGPVkCgYEApAfWEDciS+4V8iEnyLcWg/EOkmCMbc47coFeWpXC5Vkdsz9+WsEJMPJxqfiCdLtI1IZjJpJyLeXQGXRSQ+jCfL3CmmDxZw4vjkQzpGHKqKxXvV+wf1VWzxqUe6w0LrUOKtZllzC5ycuv0ZEQa9cCG4mOZkQJkpfw473CKMWK4Js=";
    //医保服务方公钥（平台）
    private static String insuranceServicePublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiogfSuor71SXVPnP33dNL08/sKETWGgG5CL0jzdxQfj6xleMt9rnguPXLDJnGUtq+oQFWMgGDDbY5txH4Cy9WuSMMLpcgbrtf2ca3H2oBuqdfhMXO+/8afMG/o5dQCbAesiNoHPqgfUoWHY9KGT1092pF0J5MdrvItugicRUKlNgTxvMAtI4rWA/xho7BgRIje8fjPayMvAJiNmP8yYVMc6lWurUHIXCag08bQ9E+QFxIBCrNIQ/4sAsCvrAjrzvW6f/w3hi/kdXlaAJ40GTgQuzoCauRDTKErKLxgifQ7sc9wZJqM1/65IWcVOnOgDFq9e//eaqwOcjkm7Xz4W+OQIDAQAB";
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

//    public static boolean rsa256CheckContent(String content, String sign) {
//        return rsa256CheckContent(content, sign, cPublicKey);
//    }

    /**
     * SHA256WithRSA 验签
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean rsa256CheckContent(String content, String sign, String publicKey) {
        try {
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
            logger.error("rsa256CheckContent failure!RSAcontent:{},sign:{}", content, sign, e.getStackTrace());
            e.printStackTrace();
            return false;

        }
    }

//    public static String rsa256Sign(String content) {
//        return rsa256Sign(content, cPrivateKey);
//    }

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

//    public static void main(String[] args) throws Exception {
//        /**
//         * 生生产RSA密钥对
//         // 服务方公私钥
//         Map<String, Object> map = SignatureUtils.genKeyPair();
//         String sPrivateKey = SignatureUtils.getPrivateKey(map);
//         String sPublicKey = SignatureUtils.getPublicKey(map);
//         System.out.println("sPrivateKey:" + sPrivateKey);
//         System.out.println("sPublicKey:" + sPublicKey);
//         */
//        // 商户公私钥
////        Map<String, Object> cKeymap = SignatureUtils.genKeyPair();
////        String cPrivateKey = SignatureUtils.getPrivateKey(cKeymap);
////        String cPublicKey = SignatureUtils.getPublicKey(cKeymap);
////        System.out.println("cPrivateKey:" + cPrivateKey);
////        System.out.println("cPublicKey:" + cPublicKey);
//
//        /**
//         * 商户请求报文加签
//         */
//        Map<String, String> requestMap = new HashMap<String, String>();
//        requestMap.put("timestamp", "2017-03-07 09:47:34");
//        requestMap.put("mchId", "ygkz1483344259595");
//        requestMap.put("mchUserId", "47");
//        requestMap.put("attach", "a=259&中文=英文");
//        requestMap.put("medicareUserName", "沙参");
//        requestMap.put("mchOrderNo", "1488867623");
//        requestMap.put("productName", "扶他林");
//        requestMap.put("productDesc", "扶他林 双氯芬酸二乙胺乳胶剂20克 关节疼痛 腰肌劳损 扭伤......");
//        requestMap.put("mchPlaceOrderTime", "2017-03-07 09:47:34");
//        requestMap.put("orderAmount", "1");
//        requestMap.put("ygkzUserId", "601665370189082624");
//
//        String content = "appId=sun7b8f2b1fcdeb4ca&attach=%7B%22doctorId%22%3A17662%2C%22doctorName%22%3A%22%E5%8D%A2%E7%BF%A0%E6%B6%B5%22%2C%22deptId%22%3A%223248%22%2C%22deptName%22%3A%22%E7%94%B5%E8%84%91%E5%AE%A4%E6%B5%8B%E8%AF%95%E7%94%A8%22%2C%22districtDeptName%22%3A%22%E9%99%A2%E6%9C%AC%E9%83%A8%22%2C%22regDate%22%3A%222018-04-10%22%2C%22timeId%22%3A%224%22%2C%22startTime%22%3A%2216%3A30%22%2C%22endTime%22%3A%2217%3A00%22%2C%22treatFee%22%3A%221000%22%2C%22_treatFee%22%3A10%2C%22patientName%22%3A%22test%22%7D&channelCode=alipay_h5&merchantNo=9683099884166530&nonceStr=394205af911f498885a9b2b13a99767b&notifyUrl=http://s438pc.natappfree.cc/esb/followupApp/v1/medicalJournal/notify/payCashNotify&outOrderNo=1522219610827&returnUrl=http://58.248.185.213:8081/patientwebapp/#/paymentsuccess&signMode=RSA&subject=测试&timeStamp=2018-04-93 11:35:11&tradeTotalFee=1000";
//        String sign = SignatureUtils.rsa256Sign(content, cPrivateKey);
//        System.out.println(sign);
//
////        requestMap.put("appId", "sun7b8f2b1fcdeb4ca");
////        requestMap.put("merchantNo", "9683099884166530");
////        requestMap.put("cashierId", "e6c21a8832f247f8bd0454b70291966c");
////        requestMap.put("nonceStr", "761fe7ef932d49c2b690f1ed10c0ff80");
////        requestMap.put("timeStamp", "2018-03-21 11:51:06");
////        requestMap.put("signMode", "RSA");
//
////        String content = SignatureUtils.getSortContent(requestMap);
////        System.out.println("商户请求参数，待签名字符串：" + content);
////        String sign1 = SignatureUtils.rsa256Sign(content, cPrivateKey);
////        requestMap.put("sign", sign1);
////        String requestJson = JsonUtils.toJSon(requestMap);
////        System.out.println("商户请求报文：" + JsonUtils.toJSon(requestMap));
//
//        /**
//         * 服务方验签
//         */
////        Map<String, String> paramsMap = JsonUtils.readValue(requestJson, HashMap.class);
////        String sign2 = paramsMap.get("sign");
////        paramsMap.remove("sign");//sign不参与验签
//        //参数排序
//        String sign2 = "S6ZsvLlAo/IT3ktrUvqaMLaIEyek24kXMxKrXJ0QTcX+oUvPZ9TtHj7wSpflShUg2B4QPfJSYDM5w8oyKeWy1EqgGksylh2zTIRVc7sY87gF93/QijheB6jJfdLWr4sQvozJdMF3J/dITP8fsHrQ1q3NfRElAPG0f5cxpw5RSQuXs2XSbs3rloCZpDHcteY7M0Gkx4AuTfd6wbO41dNMt/AEB9SUIGJpKkxtpW8A8Gg7ZyY9j3YUOESX87K6ZE13OwKkzgMxVS0zebbTcBDY0UNFMzBd+0N2b6ljAcvrLO0ypXJ1Qe6nRQ0NoXlfi5CdQpCj2x7nFszH1RAQy57Ulw==";
////        String sortContent = SignatureUtils.getSortContent(paramsMap);
//        String sortContent = "agtTradeNo=2018032821001004980556550655&appId=sun7b8f2b1fcdeb4ca&attach=26cb8cbb56e64d1ab2b73c3dfba21ee8&channelCode=alipay_h5&merchantNo=9683099884166530&nonceStr=187a0d021b7e4aaeb1bf767724cd585c&outOrderNo=1003&payTime=2018-03-28 15:00:16&platformCode=alipay&signMode=RSA&timeStamp=2018-03-28 15:00:16&totalFee=1&tradeNo=3010208201803289999990&tradeState=SUCCESS";
//        System.out.println("sign:" + sign2);
//        System.out.println("sortContent:" + sortContent);
//        System.out.println("cPublicKey:" + cPublicKey);
//        //验签
//        boolean checkStatus = SignatureUtils.rsa256CheckContent(sortContent, sign2, cPublicKey);
//        System.out.println("服务方验签结果：" + checkStatus);
//    }


    public static void main(String[] args) throws Exception {
        /**
         * * 商户请求报文加签
         */
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("idCard", "440804198810150850");
        requestMap.put("userName", "庞华福");
        requestMap.put("phoneNumber", "18665611234");
        requestMap.put("redirectUrl", "http://localhost:2017/esb/followupApp/v1/medicalJournal/medNotify/payMedAuthCallBack");
        requestMap.put("mchId", "ygkz1483344243210");
        requestMap.put("mchUserId", "191056");
        requestMap.put("timestamp", "2018-04-12 14:13:28");
        requestMap.put("attach", "a=1&b=yct");
        //排序
        String sortContent = SignatureUtils.getSortContent(requestMap);
        //加密
        String sign = SignatureUtils.rsa256Sign(sortContent, insurancePrivateKey);



        /**
         * 服务方验签
         */
        Map<String, String> responseMap = new HashMap<String, String>();
        responseMap.put("resultCode", "0");
        responseMap.put("resultMessage", "响应成功");
        responseMap.put("mchId", "ygkz1483344243210");
        responseMap.put("mchUserId", "191056");
        responseMap.put("timestamp", "2018-04-12 14:28:09");
        responseMap.put("attach", "a=1&b=yct");
//        responseMap.put("sign", "OiBvNotHQXZbWfEb1jfEIYQGNV9DSFG+vaxF5t3W06CUpO28hNGXUAHTdov8moj8bU4AsL381KNU5K2c8IKkFQjwattxNaz3u91rG6rf7scQmnYQoB/29abXKpRUOac7Z8Vwvvphmh9jVkm55zVyPIRCYNV3XtHXWmSqXUsL3ncET4Bfe9udwgruCjLye7xkhFsfONahxonItcqbC/K4NFOJGYNO/TdKB4smdQeUh3elmO1oIxTwdaTmpeV2EaL9Tm0AZRlfhrQ38MJjI26Yxyh0f9XrQ9qEbe5M6XW6Mg74OcVIOP7naiwRfcUV2a8jY8uz11K2AOIoOjQsX65k+w==");
        responseMap.put("ygkzUserId", "");
        responseMap.put("ygkzPhoneAccount", "18665611234");
        responseMap.put("ygkzUserName", "");
        responseMap.put("userName", "庞华福");
        responseMap.put("authStatus", "1");
        responseMap.put("authCode", "958482eef6c1c079");
        //排序
        String sortRespContent = SignatureUtils.getSortContent(responseMap);
        String sign2 = "C+ePBwrxww4Vz6ESw2OMUG9KlprnOcIrKmqHdg5Z2G1NLPXaUQ3or+nEGz/gMBs9dINejHl3cB9WVka8etnMf9xb4zhHsrWtOBYJv0ivy9x5qPVNHxQMVUXcyC0afJWyMTfz9ZeDh7RLcZ/gFYK1zkfn9sR4D+jzEk3UAvNvNy8GGscUWqCCezFHo+tauQfmpItkenSJWYN58wflErd4jaqsGEdn8rL94fE8vukdCGwo63U8VZ+2kMwbsuKThj8tazGUP3TCJY7M+nAeQyz4/vPuGCvpn4m7+s69ZG41ZiyuBA6hEYtR2VfQAVHjwiXvmGyc61L0YgClbIztWe8hEA==";
        //验签
        boolean checkStatus = SignatureUtils.rsa256CheckContent(sortRespContent, sign2, insuranceServicePublicKey);






    }
}
