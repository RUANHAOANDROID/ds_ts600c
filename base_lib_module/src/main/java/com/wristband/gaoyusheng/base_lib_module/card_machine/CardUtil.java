package com.wristband.gaoyusheng.base_lib_module.card_machine;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by gaoyusheng on 17/3/21.
 */

public class CardUtil {
    public static String HexToBinaryStr(String hexStr) {
        return Integer.toBinaryString(Integer.parseInt(hexStr, 16));
    }

    public static String get4CharFromEnd(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() > 4) {
            return str.substring(str.length() - 4);
        }
        return str;
    }

    public static void PrintHexChar(byte[] bytes) {
        String resultValue = "";
        for (byte oneByte : bytes) {
            int value = oneByte & 0xff;
            resultValue = resultValue + Integer.toHexString(value) + " ";
        }
        Log.e("card data", "card data:" + resultValue);
    }
}
