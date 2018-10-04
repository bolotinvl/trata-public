package com.bolotin.trata.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormatter {

    private static DecimalFormat DECIMAL_FORMATTER;

    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#0.##", decimalFormatSymbols);
    }

    public static String format(Double number) {
        return DECIMAL_FORMATTER.format(number);
    }
}