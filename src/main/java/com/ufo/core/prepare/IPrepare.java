package com.ufo.core.prepare;

import com.ufo.core.exception.PreparationFailedException;
import com.ufo.core.prepare.bean.PreparationResult;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Interface for test environment preparations before test execution
 */
public interface IPrepare {

    /**
     * Runs fully configured prepare runner
     * Throws PreparationFailedException if runner doesn't executed successfully
     *
     * @return runner result object
     */
    public PreparationResult run() throws PreparationFailedException;

    /**
     * Retrieves result from runner execution
     *
     * @return Runner Result object or null
     */
    public abstract PreparationResult getResult();

    /**
     * Starts runner process action
     */
    public void startProcess() throws PreparationFailedException;

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
