package com.retail.experience.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleFormatterTest {

    @Test
    void format() {
        Assertions.assertEquals(10.00, DoubleFormatter.format(10.000));
    }
}