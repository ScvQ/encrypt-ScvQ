package cn.spiderpig.demo.asym;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;


public class DHMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws Exception{
        
        jdkDH();
        
    }
     
    public static void jdkDH() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalStateException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        
        //初始化发送密钥
        KeyPairGenerator senderkeyPairGenerator = KeyPairGenerator.getInstance("DH");
        senderkeyPairGenerator.initialize(512);
        KeyPair senderKeyPair = senderkeyPairGenerator.generateKeyPair();
        //发送方公钥，发送给接收方（网络、文件等）
        byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();
        
        //初始化接受方密钥
        KeyFactory receiverkeyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
        PublicKey senderPublicKey = receiverkeyFactory.generatePublic(x509EncodedKeySpec);
        DHParameterSpec dhParameterSpec = ((DHPublicKey)senderPublicKey).getParams();
        KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
        receiverKeyPairGenerator.initialize(dhParameterSpec);
        KeyPair receiverKeypair = receiverKeyPairGenerator.generateKeyPair();
        PrivateKey receiverPrivateKey = receiverKeypair.getPrivate();
        byte[] receiverPublicKeyEnc = receiverKeypair.getPublic().getEncoded();
        
        //接收方构建本地密钥
        KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
        receiverKeyAgreement.init(receiverPrivateKey);
        receiverKeyAgreement.doPhase(senderPublicKey, true);
        SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");
        
        //发送方构建本地密钥
        KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
        x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
        PublicKey receiverPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
        KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
        PrivateKey senderPrivateKey = senderKeyPair.getPrivate();
        senderKeyAgreement.init(senderPrivateKey);
        senderKeyAgreement.doPhase(receiverPublicKey,true);
        SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");
        
        //接收方和发送方本地密钥的比较
        if(Objects.equals(receiverDesKey, senderDesKey)){
            System.out.println("双方密钥相同");
        }
        
        //加密
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
        byte[] result = cipher.doFinal(str.getBytes());
        System.out.println("JDK DH加密后："+Base64.encodeBase64String(result));
        
        //解密
        cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
        result = cipher.doFinal(result);
        System.out.println("JDK DH解密后："+new String(result));
        
    }

}
