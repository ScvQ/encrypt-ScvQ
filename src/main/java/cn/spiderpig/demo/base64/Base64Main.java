package cn.spiderpig.demo.base64;

import java.io.IOException;

import org.bouncycastle.util.encoders.Base64;

//import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 
 * @author ScvQ
 *
 */
public class Base64Main {
    
    private static String str = "spiderpig";
    
    public static void main(String[] args) throws IOException{
        
        jdkBase64();
        //commonsCodecBase64();
        bcprovBase64();
        
    }
    
    /**
     * bouncycastle加解密
     */
    public static void bcprovBase64(){
        
        //加密
        byte[] encode = Base64.encode(str.getBytes());
        System.out.println("加密后："+new String(encode));
        
        //解密
        byte[] decode = Base64.decode(encode);
        System.out.println("解密后："+new String(decode));
    }
    
    /**
     * apache加解密
     */
//    public static void commonsCodecBase64(){
//        
//        //加密
//        byte[] encode = Base64.encodeBase64(str.getBytes());
//        System.out.println("加密前："+new String(encode));
//        
//        //解密
//        byte[] decode = Base64.decodeBase64(encode);
//        System.out.println("解密后："+new String(decode));
//    }
    
    /**
     * jdk自带的加解密
     * @throws IOException
     */
    public static void jdkBase64() throws IOException{
        
        //加密
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(str.getBytes());
        System.out.println("加密后："+encode);
        
        //解密
        BASE64Decoder base64decoder = new BASE64Decoder();
        String decoder = new String(base64decoder.decodeBuffer(encode));
        System.err.println("解密后："+decoder);
    }

}
