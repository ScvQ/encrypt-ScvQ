package cn.spiderpig.demo.sign;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

public class DSAMain {
    
    private static String str = "spiderpig";

    public static void main(String[] args) throws Exception{
        jdkDSA();
        
    }
    
    public static void jdkDSA() throws Exception{
        
        //初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
        
        //执行签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);
        signature.update(str.getBytes());
        byte[] result = signature.sign();
        System.out.println("JDK DSA签名："+Hex.encodeHexString(result));
        
        //验证签名
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("DSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);
        signature.update(str.getBytes());
        boolean bool = signature.verify(result);
        System.out.println("JDK DSA验证："+bool);
        
    }
    
}
