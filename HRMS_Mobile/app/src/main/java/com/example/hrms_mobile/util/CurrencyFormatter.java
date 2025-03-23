package com.example.hrms_mobile.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static String format(double amount) {
        return CURRENCY_FORMAT.format(amount);
    }
}

