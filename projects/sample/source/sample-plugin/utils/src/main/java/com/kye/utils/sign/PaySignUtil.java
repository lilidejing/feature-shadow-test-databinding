package com.kye.utils.sign;

import com.kye.pda.utilcode.util.Logger;
import com.kye.utils.sign.encoder.BASE64Decoder;
import com.kye.utils.sign.encoder.BASE64Encoder;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;



/**
 * 支付签名验证工具类，用例参见com.kye.pad.base.modal.PaymentModel
 */
public class PaySignUtil {

    public static String privateKeyStr="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMQpc0DXRAlQryJhgFg0RHI6wDjn\r\n" +
            "YdPJFhJG870vEop9ertJllCDaAG9JqkdOzZW1AzTyX8Uw3KgBSQsO0KbF/RSXdJ6bWF7vfMLlgct\r\n" +
            "kfmaaGFJIzHrL3n7w47IWzhHOcQS4JpnrbSy2/6sEYPh8kRkfiToczq7s1Fpt6Ca27vxAgMBAAEC\r\n" +
            "gYA0kM77vzPSMzbbRbl46Xi2C5xbI1YJxMcPWsgSGD0ls35iFdQb+zqxzJhBlT260AW+8jn6vfuq\r\n" +
            "CcuW5+txa9I9BHzZI++Z83Mn8Njz4tPFPCAQp3Kp0LuCXi0QtwiKsfkNOh07xCPuOax8rCw3X3fs\r\n" +
            "6qrC+K2hV2HMEOYucfFwYQJBAOS3Xu5iQA9G1i4m6qvVBABO5V7XgQjak+tFlQXjGaSA5I0MkmuX\r\n" +
            "8G9pP7KcXQ6Ay7A0PYCqmKluXi4YlKZlVB0CQQDbj+u8qUq4KWW/413BnoWYKAyzStO7VWhMnZg1\r\n" +
            "m87VgwmiElWwWQTut3xZKCJA3i48lnAlKIyKgywpBHuGYRblAkEA4fddPi4p1i4JO63NWplpi5hn\r\n" +
            "O021OXcif2bOtfNKiWGi7MwH+W8y6A/XE/MyaTrMgzkJro5dp7/YIri8XK1t7QJABjRe+Qnfrqyc\r\n" +
            "z3thXuNX6yDWmwp2SUj0lZWug3VPtwDo+MeD3GPnm8dboDleHm8o51Vo/ePTdOaYDOCvjPCZcQJA\r\n" +
            "XMDOGrwlT/PhyFwiRzqP2hMR9L7R/OuHmWengmwXL2kZWZ82yNk3ImeXDqzrNsS+XKB5TOsa/V15\r\n" +
            "aan3W/9VJw==";

    public static  String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEKXNA10QJUK8iYYBYNERyOsA452HTyRYSRvO9\r\n" +
            "LxKKfXq7SZZQg2gBvSapHTs2VtQM08l/FMNyoAUkLDtCmxf0Ul3Sem1he73zC5YHLZH5mmhhSSMx\r\n" +
            "6y95+8OOyFs4RznEEuCaZ620stv+rBGD4fJEZH4k6HM6u7NRabegmtu78QIDAQAB";


    /**
     * 定义加密方式
     */
    private final static String KEY_RSA = "RSA";
    /**
     * 定义签名算法
     */
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";
    /**
     * 定义公钥算法
     */
    private final static String KEY_RSA_PUBLICKEY = "RSAPublicKey";
    /**
     * 定义私钥算法
     */
    private final static String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";

    /**
     * 生产公私钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> generateKeyPair() throws Exception {
        return generateKeyPair(1024);
    }

    /**
     * 生产公私钥
     * @param keysize
     * @return
     * @throws Exception
     */
    public static Map<String, Object> generateKeyPair(int keysize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keysize, new SecureRandom(UUID.randomUUID().toString().getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_RSA_PRIVATEKEY, privateKey);
        map.put(KEY_RSA_PUBLICKEY, publicKey);
        return map;
    }

    public static KeyFactory createKeyFactory() throws NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
        return keyFactory;
    }

    /**
     * 创建签名对象
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Signature createSignature() throws NoSuchAlgorithmException {
        Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
        return signature;
    }


    /**
     * 组建公钥对象
     * @param publicKeyStr
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static PublicKey buildPublicKey(String publicKeyStr) {
        PublicKey publicKey = null;
        try {
            byte[] publicKeyBytes = BASE64decode(publicKeyStr);
            // 创建编码说明对象
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            publicKey = createKeyFactory().generatePublic(encodedKeySpec);
        } catch (Exception e) {
            Logger.e("创建公钥失败");
        }
        return publicKey;
    }

    /**
     * 组建私钥对象
     * @param privateKeyStr
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static PrivateKey buildPrivateKey(String privateKeyStr) {
        PrivateKey privateKey = null;
        try {
            byte[] privateKeyBytes = BASE64decode(privateKeyStr);
            EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKey = createKeyFactory().generatePrivate(encodedKeySpec);
        } catch (Exception e) {
            Logger.e("创建私钥失败");
        }
        return privateKey;
    }

    /**
     * RSA使用私钥对象加密数据生成数字签名
     * @param encryptData 要加密的数据
     * @param privateKey
     */
    public static String sign(byte[] encryptData, PrivateKey privateKey) {
        String signData = null;
        try {
            Signature signature = createSignature();
            signature.initSign(privateKey);
            signature.update(encryptData);
            signData = BASE64ecode(signature.sign());
        } catch (Exception e) {
            Logger.e("使用私钥对象加密数据生成数字签名时出现异常");
        }
        return signData;
    }

    /**
     * RSA使用公钥对象对返回数据和数字签名进行校验
     * @param retunData 返回的数据
     * @param publicKey
     * @param signData 数字签名
     * @return
     */
    public static boolean verify(byte[] retunData, PublicKey publicKey, String signData) {
        boolean flag = false;
        try {
            Signature signature = createSignature();
            signature.initVerify(publicKey);
            signature.update(retunData);
            // 返回数据和数字签名进行校验
            flag = signature.verify(BASE64decode(signData));
        } catch (Exception e) {
            Logger.e("使用公钥对象对返回数据和数字签名进行校验时出现问题");
        }
        return flag;
    }

    /**
     * 根据私、公钥加密数据
     * @param data 待加密数据
     * @param key
     * @return
     */
    public static String encryptByKey(byte[] data, Key key) {
        byte[] result = null;
        try {
            KeyFactory keyFactory = createKeyFactory();
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            Logger.e("根据私、公钥加密数据时出现异常");
        }
        return BASE64ecode(result);
    }

    /**
     * 根据私、公钥解密数据
     * @param encryptStr 加密数据
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String decryptByKey(String encryptStr, Key key) {
        String result = null;
        try {
            byte[] data=BASE64decode(encryptStr);
            KeyFactory keyFactory = createKeyFactory();
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = new String(cipher.doFinal(data));
        } catch (Exception e) {
            Logger.e("根据私、公钥解密数据时出现异常", e);
        }
        return result;
    }

    /**
     * 获得私钥串
     * @param map
     * @return
     */
    public static String getPrivateKeyStr(Map<String, Object> map) {
        Key privateKey = (Key) map.get(KEY_RSA_PRIVATEKEY);
        return BASE64ecode(privateKey.getEncoded());
    }

    /**
     * 获得公钥串
     * @param map
     * @return
     */
    public static String getPublicKeyStr(Map<String, Object> map) {
        Key publicKey = (Key) map.get(KEY_RSA_PUBLICKEY);
        return BASE64ecode(publicKey.getEncoded());
    }

    /**
     * base64加密
     * @param sign
     * @return
     */
    private static String BASE64ecode(byte[] sign) {
        return new BASE64Encoder().encode(sign);
    }

    /**
     * base64解码
     * @param data
     * @return
     * @throws IOException
     */
    private static byte[] BASE64decode(String data) throws IOException {
        return new BASE64Decoder().decodeBuffer(data);
    }


    public static void main(String[] args) throws Exception {
        /*
         * Map<String, Object> map = generateKeyPair(); String privateKeyStr =
         * getPrivateKeyStr(map); String publicKeyStr = getPublicKeyStr(map); PrivateKey
         * privateKey = buildPrivateKey(privateKeyStr); PublicKey publicKey =
         * buildPublicKey(publicKeyStr); System.out.println("私钥： \n" + privateKeyStr);
         * System.out.println("公钥: \n" + publicKeyStr);
         */

        String privateKeyStr="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMQpc0DXRAlQryJhgFg0RHI6wDjn\r\n" +
                "YdPJFhJG870vEop9ertJllCDaAG9JqkdOzZW1AzTyX8Uw3KgBSQsO0KbF/RSXdJ6bWF7vfMLlgct\r\n" +
                "kfmaaGFJIzHrL3n7w47IWzhHOcQS4JpnrbSy2/6sEYPh8kRkfiToczq7s1Fpt6Ca27vxAgMBAAEC\r\n" +
                "gYA0kM77vzPSMzbbRbl46Xi2C5xbI1YJxMcPWsgSGD0ls35iFdQb+zqxzJhBlT260AW+8jn6vfuq\r\n" +
                "CcuW5+txa9I9BHzZI++Z83Mn8Njz4tPFPCAQp3Kp0LuCXi0QtwiKsfkNOh07xCPuOax8rCw3X3fs\r\n" +
                "6qrC+K2hV2HMEOYucfFwYQJBAOS3Xu5iQA9G1i4m6qvVBABO5V7XgQjak+tFlQXjGaSA5I0MkmuX\r\n" +
                "8G9pP7KcXQ6Ay7A0PYCqmKluXi4YlKZlVB0CQQDbj+u8qUq4KWW/413BnoWYKAyzStO7VWhMnZg1\r\n" +
                "m87VgwmiElWwWQTut3xZKCJA3i48lnAlKIyKgywpBHuGYRblAkEA4fddPi4p1i4JO63NWplpi5hn\r\n" +
                "O021OXcif2bOtfNKiWGi7MwH+W8y6A/XE/MyaTrMgzkJro5dp7/YIri8XK1t7QJABjRe+Qnfrqyc\r\n" +
                "z3thXuNX6yDWmwp2SUj0lZWug3VPtwDo+MeD3GPnm8dboDleHm8o51Vo/ePTdOaYDOCvjPCZcQJA\r\n" +
                "XMDOGrwlT/PhyFwiRzqP2hMR9L7R/OuHmWengmwXL2kZWZ82yNk3ImeXDqzrNsS+XKB5TOsa/V15\r\n" +
                "aan3W/9VJw==";

        String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEKXNA10QJUK8iYYBYNERyOsA452HTyRYSRvO9\r\n" +
                "LxKKfXq7SZZQg2gBvSapHTs2VtQM08l/FMNyoAUkLDtCmxf0Ul3Sem1he73zC5YHLZH5mmhhSSMx\r\n" +
                "6y95+8OOyFs4RznEEuCaZ620stv+rBGD4fJEZH4k6HM6u7NRabegmtu78QIDAQAB";
        PrivateKey privateKey = buildPrivateKey(privateKeyStr);
        PublicKey publicKey = buildPublicKey(publicKeyStr);

        System.out.println("公钥加密--------私钥解密1");
        String word = "totalCollectionMoney=0.11&totalMoney=0.12&totalWaybillMoney=0.01";
        String encryptWord = encryptByKey(word.getBytes(), publicKey);

        String decryptWord = decryptByKey(encryptWord, privateKey);
        System.out.println("加密前: " + word + "\n" + "解密后: " + decryptWord);



        System.out.println("私钥加密--------公钥解密2");
        String hello = "totalCollectionMoney=0.11&totalMoney=0.12&totalWaybillMoney=0.01";
        String encryptHello = encryptByKey(hello.getBytes(), privateKey);
        String decryptHello = decryptByKey(encryptHello, publicKey);
        System.out.println("加密前: " + encryptHello + "\n" + "解密后: " + decryptHello);




        String sign = sign("totalCollectionMoney=0.11&totalMoney=0.12&totalWaybillMoney=0.01".getBytes(), privateKey);
        System.out.println("签名:" + sign);

        boolean flag = verify("totalCollectionMoney=0.11&totalMoney=0.12&totalWaybillMoney=0.01".getBytes(), publicKey, sign);
        System.out.println("状态:" + flag);
    }

}
