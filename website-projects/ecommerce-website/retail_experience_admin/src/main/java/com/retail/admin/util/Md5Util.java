package com.retail.admin.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    private static final Logger logger = LogManager.getLogger(Md5Util.class.getName());

    private Md5Util() {
    }

    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuilder builder = new StringBuilder("");
            for (byte b : byteDigest) {
                i = b;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    builder.append("0");
                builder.append(Integer.toHexString(i));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            logger.error("No Such Algorithm Exception", exception);
            return null;
        }
    }
}
