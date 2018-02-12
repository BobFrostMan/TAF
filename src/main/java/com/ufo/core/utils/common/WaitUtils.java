package com.ufo.core.utils.common;

import com.ufo.core.utils.logging.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Utils for different waiting types
 */
public class WaitUtils {

    /**
     * Sleeps the thread for given time in seconds
     *
     * @param sec - seconds to sleep
     */
    public static void wait(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException ex) {
            Logger.info("Sleep process failed with interrupted exception! Message: " + ex.getMessage());
        }
    }

}
