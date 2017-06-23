package cn.spiderpig.demo.asym;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ElGamalMain {
    
    private static String str = "spdierpig";
    
    public static void main(String[] args) throws Exception{
        
        bcElGamal();
        
    }
    
    public static void bcElGamal() throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException{
        
        //只提供公钥加密，私钥解密
        Security.addProvider(new BouncyCastleProvider());
        
        //初始化密钥
        AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("ElGamal");
        algorithmParameterGenerator.init(256);
        AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
        DHParameterSpec dhParameterSpec = algorithmParameters.getParameterSpec(DHParameterSpec.class);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ElGamal");
        keyPairGenerator.initialize(dhParameterSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey elGamalPublicKey = keyPair.getPublic();
        PrivateKey elGamalPrivateKey = keyPair.getPrivate();
        System.out.println("公钥："+Base64.encodeBase64String(elGamalPublicKey.getEncoded()));
        System.out.println("私钥："+Base64.encodeBase64String(elGamalPrivateKey.getEncoded()));
        
    }

}
