package cn.spiderpig.demo.sym;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class DESMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        
        jdkDES();
        
        bcDES();
        
    }
    
    public static void jdkDES() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();
        
        //key转换
        DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        Key convertSecretKey = factory.generateSecret(desKeySpec);
        
        //加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("JDK DES加密后："+Hex.encodeHexString(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("JDK DES解密后："+new String(result));
    }
    
    public static void bcDES() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException{
        
        Security.addProvider(new BouncyCastleProvider());
        
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES","BC");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesKey = secretKey.getEncoded();
        
        //key转换
        DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        Key convertSecretKey = factory.generateSecret(desKeySpec);
        
        //加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("BC DES加密后："+Hex.encodeHexString(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("JDK DES解密后："+new String(result));
        
    }

}
