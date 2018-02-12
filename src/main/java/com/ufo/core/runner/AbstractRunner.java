package com.ufo.core.runner;

import com.ufo.core.exception.RunProcessException;
import com.ufo.core.runner.entity.RunnerResult;
import com.ufo.core.utils.common.WaitUtils;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Default runner process implementation.
 * Check if runner execution finished few times with given interval
 */
public abstract class AbstractRunner implements IRunner {

    private static final int DEFAULT_ATTEMPTS_COUNT = 10;
    private static final int DEFAULT_WAIT_INTERVAL = 5;

    private int attempts;
    private int interval;

    public AbstractRunner() {
        this.attempts = DEFAULT_ATTEMPTS_COUNT;
        this.interval = DEFAULT_WAIT_INTERVAL;
    }

    public AbstractRunner(int attempts, int intervalSec) {
        this.attempts = attempts;
        this.interval = intervalSec;
    }

    /**
     * Default implementation of fully configured runner
     * if runner doesn't executed successfully invokes on fail and
     * throws RunProcessException
     *
     * @return runner result object
     * @throws RunProcessException on failure
     */
    public RunnerResult run() throws RunProcessException {
        try {
            startProcess();
        } catch (Throwable t) {
            onFail();
            throw new RunProcessException(t.getMessage());
        }

        while (attempts > 0) {
            attempts--;
            if (isFinished()) {
                if (check()) {
                    return getResult();
                } else {
                    onFail();
                    throw new RunProcessException("Runner process wasn't finished successfully!");
                }
            }
            WaitUtils.wait(interval);
        }
        onFail();
        throw new RunProcessException("Runner process was failed by timeout!");
    }

}
