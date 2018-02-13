package com.ufo.core.report.client.impl;

import com.ufo.core.report.bean.Result;
import com.ufo.core.report.client.IReportClient;
import com.ufo.core.utils.logging.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 13.02.2018<br/>
 * Time: 17:48<br/>
 */
public class RestReportClient implements IReportClient {

    private String apiUrl;
    private String methodName;

    public boolean save(Result result) {
        try {
            postResult(apiUrl + methodName, result);
            return true;
        } catch (Exception ex) {
            Logger.error("Result wasn't saved successfully. Message: " + ex.getMessage(), ex);
            return false;
        }
    }

    public boolean save(Collection<Result> results) {
        return true;
    }

    // HTTP POST request
    private void postResult(String url, Result result) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())){
                wr.writeBytes(urlParameters);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            Logger.info("Sending 'POST' request to URL : " + apiUrl);
            Logger.info("Response Code : " + responseCode);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } finally {
            if (con != null){
                con.disconnect();
            }
        }
    }
}
