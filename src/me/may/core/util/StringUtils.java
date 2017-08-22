package me.may.core.util;

import java.util.regex.Pattern;

public class StringUtils {

    public static boolean IsChinese(String str) {
        Pattern pattern = Pattern.compile("[一-龥]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 字符串转16进制
     * PU: --> 50553A
     * 在线工具：http://www.bejson.com/convert/ox2str/
     */
    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString().toUpperCase();
    }

    /**
     * 16进制转换成字符串
     * 50553A --> PU:
     * 在线工具：http://www.bejson.com/convert/ox2str/
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();

        // 50553A split into two characters 50, 55, 3A
        for (int i = 0; i < hex.length() - 1; i += 2) {
            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);
        }

        return sb.toString();
    }
}
