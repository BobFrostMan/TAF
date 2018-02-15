package com.ufo.core.report.listener;

import com.ufo.core.report.bean.Result;
import com.ufo.core.report.client.impl.RestReportClient;
import org.testng.*;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 13.02.2018<br/>
 * Time: 18:35<br/>
 * <p>
 * Simple report listener that saves test execution results to external source
 */
public class ReportListener implements ITestListener {

    private static final String PASSED = "PASSED";
    private static final String FAILED = "FAILED";
    private static final String SKIPPED = "SKIPPED";

    private static ThreadLocal<Result> resultStorage = new ThreadLocal<>();

    private RestReportClient client = new RestReportClient("http://localhost:8888", "/api");

    @Override
    public void onTestStart(ITestResult result) {
        createResult(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        saveResult(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        saveResult(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        saveResult(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //do nothing
    }

    @Override
    public void onStart(ITestContext context) {
        //context creation or bulk save preparations can be implemented here
    }

    @Override
    public void onFinish(ITestContext context) {
        //bulk save can be implemented here
    }

    private void createResult(ITestResult res) {
        Result result = new Result();
        result.setMethodName(res.getMethod().getMethodName());
        result.setTestName(getTestName(res));
        result.setDescription(res.getMethod().getDescription());
        resultStorage.set(result);
    }

    public void saveResult(ITestResult testResult) {
        Result result = resultStorage.get();
        result.setParameters(testResult.getParameters());
        result.setStartTime(testResult.getStartMillis());
        result.setEndTime(testResult.getEndMillis());
        result.setResult(getStatus(testResult.getStatus()));
        result.setAdditionalInfo(getAdditionalInfo(testResult));
        resultStorage.remove();
        client.save(result);
    }

    @SuppressWarnings("all")
    private static String getTestName(ITestResult res) {
        String testName = res.getMethod().getMethod().getAnnotation(Test.class).testName();
        return testName.length() > 0 ? testName : "Unclassified test";
    }

    private static String getStatus(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return PASSED;
            case ITestResult.FAILURE:
                return FAILED;
            default:
                return SKIPPED;
        }
    }

    private static Map<String, Object> getAdditionalInfo(ITestResult testResult) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("date", new Date());
        additionalInfo.put("suite", testResult.getTestContext().getSuite().getName());
        if (testResult.getThrowable() != null) {
            additionalInfo.put("failureReason", getStackTraceAsString(testResult.getThrowable()));
        }
        return additionalInfo;
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringBuilder builder = new StringBuilder("");
        String nl = System.getProperty("line.separator");
        builder.append(nl);
        builder.append("    ");
        builder.append(throwable.toString());

        // If it's not a thread timeout, include the stack trace too
        if (!(throwable instanceof org.testng.internal.thread.ThreadTimeoutException)) {
            for (StackTraceElement e : throwable.getStackTrace()) {
                builder.append(nl);
                builder.append("    ");
                builder.append(e.toString());
            }
        }
        return builder.toString();
    }

}
