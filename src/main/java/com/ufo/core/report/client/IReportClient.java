package com.ufo.core.report.client;

import com.ufo.core.report.bean.Result;

import java.util.Collection;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 13.02.2018<br/>
 * Time: 17:38<br/>
 * <p>
 * Client interface that defines primary report client functions
 */
public interface IReportClient {

    /**
     * Saves result to report
     *
     * @param result - result to save
     * @return true if result successfully saved
     */
    public boolean save(Result result);

    /**
     * Saves results batch to report
     *
     * @param results - results to save
     * @return true if all results successfully saved
     */
    public boolean save(Collection<Result> results);

}
