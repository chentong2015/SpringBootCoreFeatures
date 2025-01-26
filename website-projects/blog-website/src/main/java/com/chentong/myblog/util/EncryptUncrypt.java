package com.chentong.myblog.util;

/**
 * 字符串的简单的加密类: 字符串与字节数组的转换
 */
public class EncryptUncrypt {

    public static String encryptAndUncrypt(String value, char secret){
        byte[] bt = value.getBytes();
        for(int i=0; i<bt.length; i++){
            bt[i] = (byte)(bt[i]^(int)secret);
        }
        // 返回新的String的对象
        return new String(bt, 0, bt.length);
    }

    public static void main(String[] args) {
        String value = "Test code";
        // 加密和解密使用同一个密码
        char secret = 'G';
        String encrypt = encryptAndUncrypt(value, secret);
        String uncrypt = encryptAndUncrypt(encrypt, secret);
    }

}
