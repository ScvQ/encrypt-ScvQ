package cn.spiderpig.demo.sym;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class PBEMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws Exception{
        
        jdkPBE();
        
        bcPBE();
        
    }

    public static void jdkPBE() throws Exception{
        
        //初始化盐
        SecureRandom random =new SecureRandom();
        byte[] salt = random.generateSeed(8);
        
        //口令与密钥
        String password = "sun";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
        Key key = factory.generateSecret(pbeKeySpec);
        
        //加密
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] result = cipher.doFinal(str.getBytes());
        System.err.println("JDK PBE加密后："+Base64.encodeBase64String(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        result = cipher.doFinal(result);
        System.out.println("JDK PBE解密后："+new String(result));
        
    }
    
    public static void bcPBE() throws Exception{
        
        Security.addProvider(new BouncyCastleProvider());
        
        //初始化盐
        SecureRandom random =new SecureRandom();
        byte[] salt = random.generateSeed(8);
        
        //口令与密钥
        String password = "sun";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES","BC");
        Key key = factory.generateSecret(pbeKeySpec);
        
        //加密
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] result = cipher.doFinal(str.getBytes());
        System.err.println("BC PBE加密后："+Base64.encodeBase64String(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        result = cipher.doFinal(result);
        System.out.println("BC PBE解密后："+new String(result));
    }
    
}
