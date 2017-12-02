package cc.zoyn.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Easily to use Baidu API
 *
 * @author Zoyn
 * @since 2017-09-10
 */
public final class BaiduAPIUtils {

    // Prevent accidental construction
    private BaiduAPIUtils() {
    }

    /**
     * 获取IP的信息
     * <p>
     * get IP Information
     *
     * @param ip     ip
     * @param apiKey your apiKey
     * @return a JSON String
     */
    public static String getIPInformation(String ip, String apiKey) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        StringBuilder http = new StringBuilder("http://apis.baidu.com/bdyunfenxi/intelligence/ip?ip=");
        http.append(ip);

        try {
            URL url = new URL(http.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set request method
            connection.setRequestMethod("GET");
            // Fill in apikey
            connection.setRequestProperty("apikey", apiKey);
            connection.connect();

            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
