package cn.spiderpig.demo.sym;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESMain {
    
    private static String str = "spdierpig";
    
    public static void main(String[] args) throws Exception{
        
        jdkAES();
        
        bcAES();
        
    }
    
    public static void jdkAES() throws Exception{
        
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        
        //key转换
        Key key = new SecretKeySpec(keyBytes, "AES");
        
        //加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("JDK AES加密后："+Base64.encodeBase64String(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(result);
        System.out.println("JDK AES解密后："+new String(result));
        
    }
    
    public static void bcAES() throws Exception{
        
        Security.addProvider(new BouncyCastleProvider());
        
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES","BC");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        
        //key转换
        Key key = new SecretKeySpec(keyBytes, "AES");
        
        //加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("BC AES加密后："+Base64.encodeBase64String(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(result);
        System.out.println("BC AES解密后："+new String(result));
    }

}
