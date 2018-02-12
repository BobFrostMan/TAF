package com.ufo.core.runner;

import com.ufo.core.exception.RunProcessException;
import com.ufo.core.runner.entity.RunnerResult;

/**
 * Created by FOG on 12.02.2018.
 *
 * Interface for test environment preparations before test execution
 */
public interface IRunner {

    /**
     * Runs fully configured runner
     * Throws RunProcessException if runner doesn't executed successfully
     *
     * @return runner result object
     */
    public RunnerResult run() throws RunProcessException;

    /**
     * Retrieves result from runner execution
     *
     * @return Runner Result object or null
     */
    public abstract RunnerResult getResult();

    /**
     * Starts runner process action
     */
    public void startProcess() throws RunProcessException;

    /**
     * Returns true if runner finished it's execution
     *
     * @return true if runner executed
     */
    public boolean isFinished();

    /**
     * Checks that runner executed successfully
     *
     * @return true if runner successfully executed
     */
    public boolean check();

    /**
     * Action that should be executed if runner process fails
     */
    public void onFail();

    /**
     * Clean up for preparation runners after all tests executed
     */
    public void cleanUp();

}
