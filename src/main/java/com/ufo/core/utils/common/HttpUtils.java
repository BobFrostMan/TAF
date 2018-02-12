package com.ufo.core.utils.common;

import com.ufo.core.utils.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 12.02.2018<br/>
 * Time: 13:47<br/>
 */
public class HttpUtils {

    public static String get(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        Logger.info("Sending 'GET' request to URL : " + url);
        Logger.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return  response.toString();
    }
}
