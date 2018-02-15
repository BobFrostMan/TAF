package com.ufo.core.report.client.impl;

import com.ufo.core.report.bean.Result;

/**
 * Created by FOG on 15.02.2018.
 *
 * Wrapper object for result object transformation according to specific RestReportClient
 *
 * @see com.ufo.core.report.client.impl.RestReportClient
 */
public class RestResultWrapper {

    private Meta meta;
    private Result data;

    public RestResultWrapper(String type, Result result){
        this.meta = new Meta(type);
        this.data = result;
    }

    private class Meta {
        private String type;

        public Meta(String type){
            this.type = type;
        }

        @Override
        public String toString() {
            return "Meta{" +
                    "type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RestResultWrapper{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}
