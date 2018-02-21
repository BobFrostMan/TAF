package com.ufo.core.report.client.impl.rest.bean;

/**
 * Created by FOG on 20.02.2018.
 */
public class RestResponse {

    private int status;
    private String content;

    public RestResponse(String content, int status) {
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
