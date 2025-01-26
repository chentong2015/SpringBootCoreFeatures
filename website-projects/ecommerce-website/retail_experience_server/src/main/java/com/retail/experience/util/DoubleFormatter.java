package com.retail.experience.util;

public class DoubleFormatter {

    private DoubleFormatter() {
    }

    public static double format(double value) {
        return Double.parseDouble(String.format("%.2f", value));
    }
}
