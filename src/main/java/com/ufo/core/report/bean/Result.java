package com.ufo.core.report.bean;

import java.util.Arrays;
import java.util.Map;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 13.02.2018<br/>
 * Time: 11:15<br/>
 * <p>
 * Entity that represents common test execution result
 * additionalInfo may be used as metadata storage
 */
public class Result {

    private String methodName;
    private String testName;
    private String description;
    private String result;
    private long startTime;
    private long endTime;
    private Object[] parameters;
    private Map<String, Object> additionalInfo;

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Map<String, Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result1 = (Result) o;

        if (startTime != result1.startTime) return false;
        if (endTime != result1.endTime) return false;
        if (methodName != null ? !methodName.equals(result1.methodName) : result1.methodName != null) return false;
        if (testName != null ? !testName.equals(result1.testName) : result1.testName != null) return false;
        if (description != null ? !description.equals(result1.description) : result1.description != null) return false;
        if (result != null ? !result.equals(result1.result) : result1.result != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(parameters, result1.parameters)) return false;
        return additionalInfo != null ? additionalInfo.equals(result1.additionalInfo) : result1.additionalInfo == null;

    }

    @Override
    public int hashCode() {
        int result1 = methodName != null ? methodName.hashCode() : 0;
        result1 = 31 * result1 + (testName != null ? testName.hashCode() : 0);
        result1 = 31 * result1 + (description != null ? description.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (int) (startTime ^ (startTime >>> 32));
        result1 = 31 * result1 + (int) (endTime ^ (endTime >>> 32));
        result1 = 31 * result1 + Arrays.hashCode(parameters);
        result1 = 31 * result1 + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result1;
    }
}
