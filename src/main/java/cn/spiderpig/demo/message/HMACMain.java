package cn.spiderpig.demo.message;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * HmacMD、HmacSHA太多了
 * @author ScvQ
 *
 */
public class HMACMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, DecoderException {
        
        jdkHmacMD5();
        bcHmacMD5();
        
    }
    
    public static void jdkHmacMD5() throws NoSuchAlgorithmException, InvalidKeyException, DecoderException{
        //初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        //产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得密钥
        //byte[] key = secretKey.getEncoded();
        //System.out.println(key);
        byte[] key = Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a','a','a'});
        //还原密钥
        SecretKey retoreSecretKey = new SecretKeySpec(key, "HmacMD5");
        //实例化MAC
        Mac mac = Mac.getInstance(retoreSecretKey.getAlgorithm());
        //初始化MAC
        mac.init(retoreSecretKey);
        //执行摘要
        byte[] hmacMD5Bytes = mac.doFinal(str.getBytes());
        System.out.println("JDK hmacMD5："+Hex.encodeHexString(hmacMD5Bytes));
    }
    
    public static void bcHmacMD5(){
        HMac hMac = new HMac(new MD5Digest());
        hMac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaaaa")));
        hMac.update(str.getBytes(),0,str.getBytes().length);
        byte[] hmacMD5Bytes = new byte[hMac.getMacSize()];
        hMac.doFinal(hmacMD5Bytes, 0);
        System.out.println("BC hmacMD5："+org.bouncycastle.util.encoders.Hex.toHexString(hmacMD5Bytes));
    }
    
}
