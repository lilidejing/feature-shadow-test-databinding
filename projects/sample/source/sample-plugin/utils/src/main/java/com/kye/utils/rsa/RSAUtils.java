package com.kye.utils.rsa;


import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * 2 * @Author: zhangshaoming  (后台优化解密工具)
 * 3 * @Date: 2021/2/5 16:24
 * 4
 */
public class RSAUtils {

    private static final String publicModulusApp = "151950364988423624624838020832929553305575559314707838148308476786088832658718824492795808651826337858126818831681981605965139949803356948835532317890908231224592291012030455861746878535866936130956048373323489593169986769542936423118651193612975485503745108011635500035573795107468899875372882603612787282237";
    private static final String publicExponentApp = "65537";
    private static final String privateModulusApp = "151950364988423624624838020832929553305575559314707838148308476786088832658718824492795808651826337858126818831681981605965139949803356948835532317890908231224592291012030455861746878535866936130956048373323489593169986769542936423118651193612975485503745108011635500035573795107468899875372882603612787282237";
    private static final String privateExponentApp = "129293560943885796225095628908072028626354899754103523114277769931126596475478817276359282076828291356919633980022669701949219971936222290489547274168584099656232621726492326823302343676408235797949320300362017743009159821937413946042819207161246880036382807741750061274350490947486199725447014734594683224973";

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String data) throws Exception {

        if (TextUtils.isEmpty(data)) {
            return data;
        }

        //生成加密的Key
        RSAPrivateKey privateKey = getPrivateKeyApp();
        Cipher ci = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        ci.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bytes = RSABase64Helper.decode(data);
        int inputLen = bytes.length;
        int offLen = 0;
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > 128) {
                cache = ci.doFinal(bytes, offLen, 128);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            byteArrayOutputStream.write(cache);
            i++;
            offLen = 128 * i;
        }
        byteArrayOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(byteArray);
    }

    public static RSAPublicKey getPublicKeyApp() {
        try {
            BigInteger b1 = new BigInteger(publicModulusApp);
            BigInteger b2 = new BigInteger(publicExponentApp);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RSAPrivateKey getPrivateKeyApp() {
        try {
            BigInteger b1 = new BigInteger(privateModulusApp);
            BigInteger b2 = new BigInteger(privateExponentApp);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
