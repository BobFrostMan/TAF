package com.ufo.core.report.client.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufo.core.exception.NotSupportedYetException;
import com.ufo.core.report.bean.Result;
import com.ufo.core.report.client.IReportClient;
import com.ufo.core.utils.logging.Logger;
import org.apache.http.HttpStatus;

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
 * <p>
 * RestApi client for external test result reporter
 */
public class RestReportClient implements IReportClient {

    private static final String SAVE_REPORT_API = "/result/default";

    private String address;
    private String apiUrl;

    public RestReportClient() {
    }

    public RestReportClient(String address, String apiUrl) {
        this.address = address;
        this.apiUrl = apiUrl;
    }

    public boolean save(Result result) {
        try {
            RestResultWrapper resultWrapper = new RestResultWrapper("default", result);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String jsonRes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultWrapper);
            Logger.info(jsonRes);
            Response response = post(address + apiUrl + SAVE_REPORT_API, jsonRes);
            if (response.getStatus() == HttpStatus.SC_OK) {
                return true;
            } else {
                Logger.error("Failed to save results! Remote server " + address + " responds with code:" + response.getStatus());
            }
        } catch (Exception ex) {
            Logger.error("Result wasn't saved successfully. Message: " + ex.getMessage(), ex);
        }
        return false;
    }

    public boolean save(Collection<Result> results) {
        throw new NotSupportedYetException("Save results in bulk are not implemented for RestReportClient yet!");
    }

    private Response post(String endpoint, String payload) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(payload);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            Logger.info("Sending 'POST' request to URL : " + endpoint);
            Logger.info("Response Code : " + responseCode);
            StringBuffer response = new StringBuffer();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return new Response(response.toString(), con.getResponseCode());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private class Response {

        private int status;
        private String content;

        public Response(String content, int status) {
            this.content = content;
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "status=" + status +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
