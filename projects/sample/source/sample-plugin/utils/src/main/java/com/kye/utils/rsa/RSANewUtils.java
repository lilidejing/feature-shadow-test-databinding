package com.kye.utils.rsa;

import android.text.TextUtils;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSANewUtils {


    /**
     * 传输数据加密公钥
     */
    private static final String publicModulus = "137589959567648827918424281380962171945120851759382150851387573770320464703006347801171262318021501569817088398171696813202319402148181838939820650364709928671600308175585808375800633435714335520791936647296570288518687196814892389092260429690494916535297509825937376321976855042688643262348281075627940491123";
    private static final String publicExponent = "65537";

    /**
     * 接受数据解密私钥
     */
    private static final String privateModulusApp = "151950364988423624624838020832929553305575559314707838148308476786088832658718824492795808651826337858126818831681981605965139949803356948835532317890908231224592291012030455861746878535866936130956048373323489593169986769542936423118651193612975485503745108011635500035573795107468899875372882603612787282237";
    private static final String privateExponentApp = "129293560943885796225095628908072028626354899754103523114277769931126596475478817276359282076828291356919633980022669701949219971936222290489547274168584099656232621726492326823302343676408235797949320300362017743009159821937413946042819207161246880036382807741750061274350490947486199725447014734594683224973";

    public static void main(String[] args) throws Exception {
//		String str = "085455";
//		String beginStr=encryptByPublicKey(str);
//		System.out.println("加密之后:"+beginStr);

        String endStr = decryptByPrivateKeyInGetData("33E0D5DC65AAB7F009C1338DCE6D345E58F236DE1897EF0192C65EC2FD3B9B7234BC98A94C1B9BF61AF7A3BCF703004C6EC130B138DC59574ECB020A1296D6FD3AD8A87A4A14A4CE85887814952193292B591AF9831412C3EC14431A8C05123B605A782F5CC64518278C14D3AAD564D948679A683C946B1F5414278CD23206B8");
        System.out.println("加密之后:" + endStr);
    }
    /**
     * 生成公钥和私钥
     */
	/*public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		System.out.println("公钥Modulus:"+publicKey.getModulus());
		System.out.println("公钥Exponent:"+publicKey.getPublicExponent());
		System.out.println("私钥Modulus:"+privateKey.getModulus());
		System.out.println("私钥Exponent:"+privateKey.getPrivateExponent());
		map.put("public", publicKey);
		map.put("private", privateKey);
		return map;
	}*/

    /**
     * 传输接口数据约定公钥加密
     */
    public static String encryptByPublicKey(String data) throws Exception {
        /* 这块将padding变成和服务器默认的一致 */
        RSAPublicKey publicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        // 如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 接受接口数据约定的私钥解密
     */
    public static String decryptByPrivateKeyInGetData(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }
        try {
            RSAPrivateKey privateKey = getPrivateKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 模长
            int key_len = privateKey.getModulus().bitLength() / 8;
            byte[] bytes = data.getBytes();
            byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
            // 如果密文长度大于模长则要分组解密
            String ming = "";
            byte[][] arrays = splitArray(bcd, key_len);
            for (byte[] arr : arrays) {
                ming += new String(cipher.doFinal(arr));
            }
            return ming;
        } catch (Exception e) {
            return data;
        }
    }

    /**
     * ASCII码转BCD码
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;
        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;
        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey() {
        try {
            BigInteger b1 = new BigInteger(publicModulus);
            BigInteger b2 = new BigInteger(publicExponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey() {
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

