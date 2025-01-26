package com.chentong.myblog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类, util的工具类
 */
public class Md5Util {

    //str 要加密的字符串
    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 获取到字符串的字节流
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            // 字符串的缓冲区域
            StringBuffer buffer = new StringBuffer("");
            // 针对每一个的字节 改变字节对应的字符形式
            for(int offset=0; offset < byteDigest.length; offset++){
                i = byteDigest[offset];
                if(i<0)
                    i += 256;
                if(i<16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString(); // 32 bits 加密
            // return buffer.toString().substring(8, 24); // 16 bits 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 单独测试加密后的密码
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(code("chentong"));
    }

}
