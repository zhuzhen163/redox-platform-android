package http.utils;

import com.redoxyt.app.common.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AesSecret {
    //public static Logger log = Logger.getLogger(AesSecret.class);

    static final String algorithmStr = BuildConfig.ALGORITHMSTR;


    static boolean isInited = false;

    /****加密**/
    private static byte[] encrypt(String content) {
        try {   //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            byte[] keyStr = getKey(SecretKey.getKey());
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            //实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance(algorithmStr);// algorithmStr

            byte[] byteContent = content.getBytes("utf-8");
            //初始化，此方法可以采用三种方式，按服务器要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, key);// ?
            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE, 　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
            byte[] result = cipher.doFinal(byteContent);
            return result; //
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(byte[] content) {
        try { //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            byte[] keyStr = getKey(SecretKey.getKey());
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            //实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance(algorithmStr);// algorithmStr

            cipher.init(Cipher.DECRYPT_MODE, key);// ?
            byte[] result = cipher.doFinal(content);
            return result; //
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password != null) {
            rByte = password.getBytes();
        } else {
            rByte = new byte[24];
        }
        return rByte;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * 加密
     */
    public static String encode(String content) {
        Logger.e("加密前参数：" + content);
        // 加密之后的字节数组,转成16进制的字符串形式输出
        return parseByte2HexStr(encrypt(content));
    }

    /**
     * 解密
     */
    public static String decode(String content) {
        // 解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        byte[] b = decrypt(parseHexStr2Byte(content));
        return new String(b);
    }

    // 测试用例
    public static void test1() {
        String content = "{\"data\":{\"draw\":\"0\",\"length\":\"10\",\"start\":\"0\"},\"items\":{\"customId\":\"3117\"},\"id\":3}";
        String pStr = encode(content);
        System.out.println("加密前：" + content);
        System.out.println("加密后:" + pStr);
        //pStr="CA9EBE1706084B370E9C7912763090D44307561C421F9D503BE5C9D700D88495";
        String postStr = decode(pStr);
        System.out.println("解密后：" + postStr);
    }

    public static void main(String[] args) {
        String original = "";
        System.out.println("原始数据:" + original);
        String postStr = decode(original);
        System.out.println("解密后：" + postStr);
        test1();
    }

}
