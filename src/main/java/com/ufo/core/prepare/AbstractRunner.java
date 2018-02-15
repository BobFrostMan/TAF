package com.ufo.core.prepare;

import com.ufo.core.exception.PreparationFailedException;
import com.ufo.core.prepare.bean.PreparationResult;
import com.ufo.core.utils.common.WaitUtils;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Default runner process implementation.
 * Check if runner execution finished few times with given interval
 */
public abstract class AbstractRunner implements IPrepare {

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
     * throws PreparationFailedException
     *
     * @return runner result object
     * @throws PreparationFailedException on failure
     */
    public PreparationResult run() throws PreparationFailedException {
        try {
            startProcess();
        } catch (Throwable t) {
            onFail();
            throw new PreparationFailedException(t.getMessage());
        }

        while (attempts > 0) {
            attempts--;
            WaitUtils.wait(interval);
            if (isFinished()) {
                if (check()) {
                    return getResult();
                } else {
                    onFail();
                    throw new PreparationFailedException("Runner process wasn't finished successfully!");
                }
            }
        }
        onFail();
        throw new PreparationFailedException("Runner process was failed by timeout!");
    }

}
