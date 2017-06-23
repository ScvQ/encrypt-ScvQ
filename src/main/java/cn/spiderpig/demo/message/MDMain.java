package cn.spiderpig.demo.message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.util.encoders.Hex;

/**
 * 结论：jdk、cc、bc的md2、md5加密是一样的
 * @author ScvQ
 *
 */
public class MDMain {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        jdkMD5();
        jdkMD2();
        
        bcMD5();
        bcMD4();
        bcMD2();
        
        ccMD5();
        ccMD2();
        
    }
    
    public static void jdkMD5() throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = messageDigest.digest(str.getBytes());
        //转为16进制显示
//        String md5str = Hex.encodeHexString(md5Bytes);
//        System.out.println("JDK MD5加密后："+md5str);
    }
    
    public static void jdkMD2() throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("MD2");
        byte[] md2Bytes = messageDigest.digest(str.getBytes());
        //转为16进制显示
//        String md5str = Hex.encodeHexString(md2Bytes);
//        System.out.println("JDK MD2加密后："+md5str);
    }
    
    public static void bcMD5(){
        Digest digest = new MD5Digest();
        digest.update(str.getBytes(), 0, str.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        System.out.println("BC MD5加密后："+Hex.toHexString(md5Bytes));
    }
    
    public static void bcMD4(){
        Digest digest = new MD4Digest();
        digest.update(str.getBytes(), 0, str.getBytes().length);
        byte[] md4Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        System.out.println("BC MD4加密后："+Hex.toHexString(md4Bytes));
    }
    
    public static void bcMD2(){
        Digest digest = new MD2Digest();
        digest.update(str.getBytes(), 0, str.getBytes().length);
        byte[] md2Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md2Bytes, 0);
        System.out.println("BC MD2加密后："+Hex.toHexString(md2Bytes));
    }
    
    public static void ccMD5(){
        String ccMd5Str = DigestUtils.md5Hex(str.getBytes());
        System.out.println("CC MD5加密后："+ccMd5Str);
    }
    
    public static void ccMD2(){
        String ccMd5Str = DigestUtils.md2Hex(str.getBytes());
        System.out.println("CC MD2加密后："+ccMd5Str);
    }
    
    

}
