package com.sfnvm.example.jabxpoc.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {
    private final ThreadLocal<DecimalFormat> format;

    public BigDecimalAdapter(String pattern) {
        format = ThreadLocal.withInitial(() -> {
            DecimalFormat df = new DecimalFormat(pattern);
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df;
        });
    }

    /**
     * Overrides the unmarshal method of the class XmlAdapter in order
     * to parse BigDecimal of the type String
     *
     * @param s String
     * @return BigDecimal
     */
    @Override
    public BigDecimal unmarshal(String s) {
        return new BigDecimal(s);
    }

    /**
     * Overrides the unmarshal method of the class XmlAdapter in order
     * to convert the passed date to an String
     *
     * @param bigDecimal BigDecimal
     * @return String
     */
    @Override
    public String marshal(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        String fmt = format.get().format(bigDecimal);
        format.remove();
        return !fmt.contains(".") ? fmt : fmt.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static final class BigDecimal2Adapter extends BigDecimalAdapter {
        public BigDecimal2Adapter() {
            super("#0.00");
        }
    }

    public static final class BigDecimal4Adapter extends BigDecimalAdapter {
        public BigDecimal4Adapter() {
            super("#0.0000");
        }
    }
}
