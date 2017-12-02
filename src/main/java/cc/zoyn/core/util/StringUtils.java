package cc.zoyn.core.util;

import java.util.regex.Pattern;

public final class StringUtils {

    // Prevent accidental construction
    private StringUtils() {}

    /**
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[一-龥]*");
        return pattern.matcher(str).matches();
    }

    /**
     * Convert String to Hex String
     */
    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char charObj : chars) {
            hex.append(Integer.toHexString((int) charObj));
        }
        return hex.toString().toUpperCase();
    }

    /**
     * Convert Hex String to String
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
