package com.mirana.frame.utils;

import com.mirana.frame.constants.SysConstants;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * AES 加解密算法
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月6日下午2:37:17
 */
public class AESUtils {
	// u8 charset
	private static final Charset CHARSET_U8     = SysConstants.DEFAULT_CHARSET;
	// u8 string
	private static final String  CHARSET_U8_STR = CHARSET_U8.name();

	// 算法：AES
	private static final String ALGORITHM = "AES";
	// 算法/模式/补码
	private static final String AESTYPE   = "AES/CBC/PKCS5Padding";

	// 16位秘钥key
	private static final String SERECT_KEY = "F104310F360042AF";
	// 16位向量偏移量
	private static final String IV_KEY     = "3467CC171DE749AE";

	/*
	 * AES加密
	 */
	public static String encode (String content) {

		String AES_encode = null;
		try {
			String serectKey = SERECT_KEY;
			String ivKey = IV_KEY;

			// 1.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(AESTYPE);
			SecretKeySpec keyspec = new SecretKeySpec(serectKey.getBytes(CHARSET_U8), ALGORITHM);
			// 2、使用CBC模式，需要一个向量iv，可增加加密算法的强度
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes(CHARSET_U8));
			// 3.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY，第三个参数为向量iv
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, iv);
			// 4.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byte_encode = content.getBytes(CHARSET_U8_STR);
			// 5.根据密码器的初始化方式--加密：将数据加密
			byte[] byte_AES = cipher.doFinal(byte_encode);
			// 6.将加密后的数据转换为字符串
			AES_encode = new String(Base64.encodeBase64(byte_AES));
			// 7. url编码
			AES_encode = URLEncoder.encode(AES_encode, CHARSET_U8_STR);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 7.将字符串返回，错误返回null
		return AES_encode;
	}

	/*
	 * AES解密
	 */
	public static String decode (String content) {

		String AES_decode = null;
		try {
			// 1.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(SERECT_KEY.getBytes(CHARSET_U8), ALGORITHM);
			// 2.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(AESTYPE);
			// 3、使用CBC模式，需要一个向量iv，可增加加密算法的强度
			IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes(CHARSET_U8));
			// 4.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY，第三个参数为向量iv
			cipher.init(Cipher.DECRYPT_MODE, key, iv);

			// 5.url解码
			content = URLDecoder.decode(content, CHARSET_U8_STR);
			// 6.将加密并编码后的内容解码成字节数组
			// byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			byte[] byte_content = Base64.decodeBase64(content);
			// 7.AES解密
			byte[] byte_decode = cipher.doFinal(byte_content);
			AES_decode = new String(byte_decode, CHARSET_U8_STR);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return AES_decode;
	}

}
