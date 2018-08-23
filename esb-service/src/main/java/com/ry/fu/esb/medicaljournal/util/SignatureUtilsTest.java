package com.ry.fu.esb.medicaljournal.util;

import com.ry.fu.esb.common.utils.JsonUtils;
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
 * @Project: sunshine_health_payment_platform 
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
public class SignatureUtilsTest {
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

	public static void main(String[] args) throws Exception {
		/** 
		 * 生生产RSA密钥对
		// 服务方公私钥
		Map<String, Object> map = SignatureUtils.genKeyPair();
		String sPrivateKey = SignatureUtils.getPrivateKey(map);
		String sPublicKey = SignatureUtils.getPublicKey(map);
		System.out.println("sPrivateKey:" + sPrivateKey);
		System.out.println("sPublicKey:" + sPublicKey);
		*/
		// 商户公私钥
//		Map<String, Object> cKeymap = SignatureUtils.genKeyPair();
//		String cPrivateKey = SignatureUtils.getPrivateKey(cKeymap);
//		String cPublicKey = SignatureUtils.getPublicKey(cKeymap);
		String cPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMl4bnfEYbofcThxsYNe7NSIQ7aXspuaTyAZ6fJ7smM/98liOlr3sO01JoSKXAhEFoU5Wqxf73bioltT4IVp6fKFXgkZS1FhbdnafSNvLj2WM6Dc3WIEVKQyTomACWCvvHGz2mYyXZbL1PhGxltp3S13/7OOtwjYvPdtywUDpXwDAgMBAAECgYBumtIyqDpqntZegXRNxfBI4aIi/PcOG9HysqdP+v69JCIZifa1eCPGphZGT1slIpQmgY048tYZM2SktNCfDb1j8Bx+uzjpsCjvm5qmmpF0AakKc89oKzUC1RfPccdieuOPcBbmIYzG2Gpuq5s+KAplP1LdPNrDYvDeW+N5GeTPKQJBAPV9rBHw6BIkVPLNVPDQeT1+WWZ6i4N+4ihLSJX7MhYmJuHohsUIBNtd4wRTpYlAdolQbAZS6x3EleXvhWHAdtcCQQDSGFccX8qIOeceyRXt5ckMhH8BV03g9f1k1Z6caGMxqxv0UPWlFT7LzixZKvDEtZSTI5E4bgTUPBZx773WqXq1AkEAyI9Bku7ESn4QQMVsDcnkI08kbsrTTDzPO6g1xwKQKvREc+/qmvIwNaAApjpIVVerAAgKGSiJGarP5CjJSdFZxQJAIcdqEhTQsnBk2Y1JKlO8rHeNGAEac2X5lMHH26vo7u5s8y4lIKK20vvWI4wE5Aux1KpuSd1eh2UV0sHSCvZ61QJBAOLtkkzWM8fGBOKbrS1JsNjWVtUgkar+2/Vr3UQAe4ccbJSm7ib3QPFf75kGoQkN63nUFpVECKJFNQEy2Ihwsgg=";
		String cPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9iOvbpg3nxAYkh00sbYRVMvCY7d0U/1XelDeBpqFC43DWKtPMW5NrkkbBHXnyDydHpiV5xeMZu3RoGEWiOSkdIelgmgWDDn+p1FUiGU54xUntNS4V0e1nFXzrddWtrPVLFQ5B3PpM3jhkcB0FDABeXTzubSNPwWdhsoQBVNOoSnJVeB8Aot3z17IOat+x/51rtzDKO3rLC3Y90E4Lvz0DXrEw8Mjc3mPYD5zd3+d/k0KDgRQMxQEGL/LQoP7H6EBuRxHloJfE2M7O1LP8AI9XeKhd9r7k6y5zC5GetrART0buzfPlKrC00PjafLzBRTa45tE9l5OsbZ5tw+kXytqwIDAQAB";

		System.out.println("cPrivateKey:" + cPrivateKey);
		System.out.println("cPublicKey:" + cPublicKey);
		
		/**
		 * 商户请求报文加签
		 */
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("timestamp", "2017-03-07 09:47:34");
		requestMap.put("mchId", "ygkz1483344259595");
		requestMap.put("mchUserId", "47");
		requestMap.put("attach", "a=259&中文=英文");
		requestMap.put("medicareUserName", "沙参");
//		requestMap.put("mchOrderNo", "1488867623");
//		requestMap.put("productName", "扶他林");
//		requestMap.put("productDesc", "扶他林 双氯芬酸二乙胺乳胶剂20克 关节疼痛 腰肌劳损 扭伤......");
//		requestMap.put("mchPlaceOrderTime", "2017-03-07 09:47:34");
//		requestMap.put("orderAmount", "1");
//		requestMap.put("ygkzUserId", "601665370189082624");
		String content = SignatureUtils.getSortContent(requestMap);
		System.out.println("商户请求参数，待签名字符串：" + content);
		String sign1 = SignatureUtils.rsa256Sign(content, cPrivateKey);
		requestMap.put("sign", sign1);
		String requestJson = JsonUtils.toJSon(requestMap);
		System.out.println("商户请求报文：" + JsonUtils.toJSon(requestMap));
		
		/**
		 * 服务方验签
		 */
		Map<String, String> paramsMap = JsonUtils.readValue(requestJson, HashMap.class);
		String sign2 = paramsMap.get("sign");
		paramsMap.remove("sign");//sign不参与验签
		//参数排序
		String sortContent = SignatureUtils.getSortContent(paramsMap);
		//验签
		boolean checkStatus = SignatureUtils.rsa256CheckContent(sortContent, sign2, cPublicKey);
		System.out.println("服务方验签结果：" + checkStatus);


	}

}
