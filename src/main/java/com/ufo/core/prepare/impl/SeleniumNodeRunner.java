package com.ufo.core.prepare.impl;

import com.ufo.core.exception.NotSupportedYetException;
import com.ufo.core.exception.PreparationFailedException;
import com.ufo.core.utils.common.CliUtils;
import com.ufo.core.utils.common.HttpUtils;
import com.ufo.core.utils.logging.Logger;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.IOException;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 12.02.2018<br/>
 * Time: 13:12<br/>
 * <p>
 * Runner that start selenium slave node
 * To implement run with proxy in selenium grid see the link below
 *
 * @link <a href="https://stackoverflow.com/questions/9884804/how-to-start-selenium-browser-with-proxy"/>
 */
public class SeleniumNodeRunner extends SeleniumGridRunner {

    private static final String DEFAULT_DRIVER_BINARY_PATH = "src/main/resources/driver/bin/";
    private static final String DEFAULT_CHROME_DRIVER_FILENAME = "chromedriver.exe";
    private static final String DEFAULT_NODE_ADDRESS = "http://localhost:5556";

    private static final String SELENIUM_SERVER_SCRIPT_NAME = "start-selenium-node.bat";

    private static final String SHUTDOWN_NODE_HOOK = "/extra/LifecycleServlet?action=shutdown";

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void startProcess() throws PreparationFailedException {
        String filePath = getSeleniumServerPath();
        Logger.info("Running selenium node by path: " + filePath);
        setVariablesForBrowser(BrowserType.CHROME);
        try {
            File dir = new File(DEFAULT_SELENIUM_PATH);
            File script = new File(DEFAULT_SELENIUM_PATH + SELENIUM_SERVER_SCRIPT_NAME);
            process = CliUtils.execute(dir, "cmd.exe", "/c", script.getAbsolutePath());
        } catch (IOException e) {
            throw new PreparationFailedException(e.getMessage());
        }
    }

    public void onFail() {
        stopNode();
    }

    public void cleanUp() {
        stopNode();
    }

    private void stopNode() {
        if (process != null) {
            Logger.info("Terminating selenium node process...");
            CliUtils.terminate(process);
            try {
                HttpUtils.get(DEFAULT_NODE_ADDRESS + SHUTDOWN_NODE_HOOK);
            } catch (IOException e) {
                Logger.error("Failed to stop Selenium node. Message: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Set system variables before start driver that suites specified browser
     *
     * @param browserName - browser to start
     */
    private void setVariablesForBrowser(String browserName) {
        if (browserName == null || browserName.length() == 0) {
            throw new IllegalArgumentException("Parameter 'browserName' shouldn't be null or empty");
        }
        switch (browserName.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", DEFAULT_DRIVER_BINARY_PATH + DEFAULT_CHROME_DRIVER_FILENAME);
                break;
            case "firefox":
            case "opera":
            case "ie":
                //fallthrough
                throw new NotSupportedYetException("Running node with browser '" + browserName + "' wasn't supported... Yet.");
        }
    }
}
