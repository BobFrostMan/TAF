package com.ufo.core.utils.common;

import com.ufo.core.utils.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 12.02.2018<br/>
 * Time: 13:47<br/>
 * <p>
 * Utility class for simple http requests
 */
public class HttpUtils {

    /**
     * Returns content by given url as String
     *
     * @param url - url to get content from
     * @return string representation content
     * @throws IOException if url is corrupted or unreachable
     */
    public static String get(String url) throws IOException {
        URL urlObj = new URL(url);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        try {
            // optional default is GET
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            Logger.info("Sending 'GET' request to URL : " + url);
            Logger.info("Response Code : " + responseCode);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response.toString();
    }
}
