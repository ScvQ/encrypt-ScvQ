package cn.spiderpig.demo.message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;


/**
 *  重点：记住jdk、bc、cc
 * @author ScvQ
 *
 */
public class SHAMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        jdkSHA1();
        jdkSHA224();
        
        bcSHA1();
        bcSHA224();
        
        ccSHA1();
        
    }
    
    public static void jdkSHA1() throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(str.getBytes());
        byte[] messageDigestBytes = messageDigest.digest();
        System.out.println("JDK SHA1："+Hex.encodeHexString(messageDigestBytes));
    }
    
    public static void jdkSHA224() throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-224");
        //MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        //MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(str.getBytes());
        byte[] messageDigestBytes = messageDigest.digest();
        System.out.println("JDK SHA224："+Hex.encodeHexString(messageDigestBytes));
    }
    
    public static void bcSHA1(){
        Digest digest = new SHA1Digest();
        digest.update(str.getBytes(), 0, str.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes, 0);
        System.out.println("BC SHA1："+org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
    }
    
    public static void bcSHA224(){
        Digest digest = new SHA224Digest();
        digest.update(str.getBytes(), 0, str.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes, 0);
        System.out.println("BC SHA224："+org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
    }
    
    public static void ccSHA1(){
        //System.out.println("CC SHA1："+DigestUtils.sha1Hex(str.getBytes()));
        System.out.println("CC SHA1："+DigestUtils.sha1Hex(str));
        System.out.println("CC SHA256："+DigestUtils.sha256Hex(str));
        System.out.println("CC SHA512："+DigestUtils.sha512Hex(str));
    }

}
