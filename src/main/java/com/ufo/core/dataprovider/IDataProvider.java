package com.ufo.core.dataprovider;

/**
 * Created by FOG on 20.02.2018.
 * <p>
 * DataProvider interface for TestNG
 */
public interface IDataProvider {

    /**
     * Returns two-dimensional array of test data, where
     * row is one set of data, for a single test run
     *
     * @see org.testng.annotations.DataProvider
     * @return two dimensional array with filled test data
     */
    public Object[][] getData();

}
