package com.retail.admin.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Md5UtilTest {

    @Test
    void encrypt() {
        String encryptedPass = Md5Util.encrypt("admin");
        Assertions.assertEquals("21232f297a57a5a743894a0e4a801fc3", encryptedPass);
    }
}