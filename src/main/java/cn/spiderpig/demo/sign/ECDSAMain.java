package cn.spiderpig.demo.sign;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

public class ECDSAMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws Exception{
        
        jdkECDSA();
        
    }
    
    public static void jdkECDSA() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        
        //初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
        
        //执行签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(privateKey);
        signature.update(str.getBytes());
        byte[] result = signature.sign();
        System.out.println("JDK ECDSA签名："+Hex.encodeHexString(result));
        
        //验证签名
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        signature = Signature.getInstance("SHA1withECDSA");
        signature.initVerify(publicKey);
        signature.update(str.getBytes());
        boolean bool = signature.verify(result);
        System.out.println("JDK ECDSA验证签名："+bool);
        
    }

}
