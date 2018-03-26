package com.ufo.core.prepare.impl;

import com.ufo.core.exception.PreparationFailedException;
import com.ufo.core.prepare.AbstractRunner;
import com.ufo.core.prepare.bean.PreparationResult;
import com.ufo.core.utils.common.CliUtils;
import com.ufo.core.utils.common.HttpUtils;
import com.ufo.core.utils.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Runner that start selenium server, for tests execution in grid
 */
public class SeleniumGridRunner extends AbstractRunner {

    protected static final String DEFAULT_SELENIUM_PATH = "src/main/resources/grid/";
    protected static final String DEFAULT_SELENIUM_SERVER_JAR_PATTERN = "selenium-server-standalone-%s.jar";
    protected static final String DEFAULT_SELENIUM_SERVER_ENDPOINT = "/grid/console?config=true&configDebug=true";

    private static final String DEFAULT_SELENIUM_SERVER_HOST = "http://localhost:4444";
    private static final String SELENIUM_SERVER_SCRIPT_NAME = "start-selenium-server.bat";

    private static final String SHUTDOWN_SELENIUM_SERVER_HOOK = "/lifecycle-manager/LifecycleServlet?action=shutdown";

    protected Process process;

    public boolean isFinished() {
        try {
            String content = HttpUtils.get(DEFAULT_SELENIUM_SERVER_HOST + DEFAULT_SELENIUM_SERVER_ENDPOINT);
            return content.length() > 0;
        } catch (Exception e) {
            Logger.debug("SeleniumGridRunner doesn't finished it's execution. Message: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean check() {
        return true;
    }

    public PreparationResult getResult() {
        return null;
    }

    public void startProcess() throws PreparationFailedException {
        String filePath = getSeleniumServerPath();
        Logger.info("Running selenium server by path: " + filePath);
        try {
            File dir = new File(DEFAULT_SELENIUM_PATH);
            File script = new File(DEFAULT_SELENIUM_PATH + SELENIUM_SERVER_SCRIPT_NAME);
            process = CliUtils.execute(dir, "cmd.exe", "/c", script.getAbsolutePath());
        } catch (IOException e) {
            throw new PreparationFailedException(e.getMessage());
        }
    }

    public void onFail() {
        stopServer();
    }

    public void cleanUp() {
        stopServer();
    }

    private void stopServer() {
        if (process != null) {
            Logger.info("Terminating selenium server (hub) process...");
            CliUtils.terminate(process);
            try {
                HttpUtils.get(DEFAULT_SELENIUM_SERVER_HOST + SHUTDOWN_SELENIUM_SERVER_HOOK);
            } catch (IOException e) {
                Logger.error("Failed to stop selenium hub. Message: " + e.getMessage(), e);
            }
        }
    }

    protected String getSeleniumServerPath() {
        //TODO: handle any version in resources path here
        return DEFAULT_SELENIUM_PATH + String.format(DEFAULT_SELENIUM_SERVER_JAR_PATTERN, "3.9.1");
    }

}
