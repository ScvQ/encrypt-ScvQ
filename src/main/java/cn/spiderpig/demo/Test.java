package cn.spiderpig.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class Test {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //MD5解密
        System.out.println(DigestUtils.md5Hex(new FileInputStream(new File("/Users/ScvQ/Downloads/apache-tomcat-9.0.0.M21.zip"))));
        
    }

}
