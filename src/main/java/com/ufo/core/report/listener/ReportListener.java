package com.ufo.core.report.listener;

import com.ufo.core.report.bean.Result;
import org.testng.*;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 13.02.2018<br/>
 * Time: 18:35<br/>
 */
public class ReportListener implements IInvokedMethodListener{

    private static final String PASSED = "PASSED";
    private static final String FAILED = "FAILED";
    private static final String SKIPPED = "SKIPPED";

    private ThreadLocal<Result> resultStorage = new ThreadLocal<>();

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Result result = new Result();
        result.setMethodName(method.getTestMethod().getMethodName());
        result.setTestName(getTestName(method));
        result.setDescription(method.getTestMethod().getDescription());
        resultStorage.set(result);
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        Result result = resultStorage.get();
        result.setParameters(testResult.getParameters());
        result.setStartTime(testResult.getStartMillis());
        result.setEndTime(testResult.getEndMillis());
        result.setResult(getStatus(testResult.getStatus()));
        result.setAdditionalInfo(getAdditionalInfo(method, testResult));
    }

    private static String getStatus(int status){
        switch (status){
            case ITestResult.SUCCESS:
                return PASSED;
            case ITestResult.FAILURE:
                return FAILED;
            default:
                return SKIPPED;
        }
    }

    private static String getTestName(IInvokedMethod method){
        String testName = method.getTestMethod().getMethod().getAnnotation(Test.class).testName();
        return testName.length() > 0 ? testName : "Unclassified test";
    }

    private static Map<String, Object> getAdditionalInfo(IInvokedMethod method, ITestResult testResult){
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("date", method.getDate());
        additionalInfo.put("suite", testResult.getTestContext().getSuite());
        return additionalInfo;
    }

}
