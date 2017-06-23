package cn.spiderpig.demo.sym;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * DESede即3DES
 * @author ScvQ
 *
 */
public class DESedeMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws Exception {
        
        jdkDESede();
        
        bcDESede();
        
    }
    
    public static void jdkDESede() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        //keyGenerator.init(168);
        keyGenerator.init(new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();
        
        //key转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key convertSecretKey = factory.generateSecret(desKeySpec);
        
        //加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("JDK DESede加密后："+Hex.encodeHexString(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("JDK DESede解密后："+new String(result));
    }
    
    public static void bcDESede() throws Exception{
        
        Security.addProvider(new BouncyCastleProvider());
        
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede","BC");
        //keyGenerator.init(168);
        keyGenerator.init(new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();
        
        //key转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        Key convertSecretKey = factory.generateSecret(desKeySpec);
        
        //加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("BC DESede加密后："+Hex.encodeHexString(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("BC DESede解密后："+new String(result));
        
    }

}
