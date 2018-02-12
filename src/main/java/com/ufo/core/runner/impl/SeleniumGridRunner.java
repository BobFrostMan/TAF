package com.ufo.core.runner.impl;

import com.ufo.core.exception.RunProcessException;
import com.ufo.core.runner.AbstractRunner;
import com.ufo.core.runner.entity.RunnerResult;
import com.ufo.core.utils.common.CliUtils;
import com.ufo.core.utils.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by FOG on 12.02.2018.
 *
 * Runner that start selenium server, for tests execution in grid
 */
public class SeleniumGridRunner extends AbstractRunner{

    private static final String DEFAULT_SELENIUM_PATH = "src/main/resources/grid/";
    private static final String DEFAULT_SELENIUM_SERVER_JAR_PATTERN = "selenium-server-standalone-%s.jar";
    private static final String DEFAULT_SELENIUM_DRIVERS_PATH = "src/main/resources/";
    private static final String DEFAULT_CHROME_DRIVER_FILENAME = "chromedriver.exe";

    private Process process;

    public boolean isFinished() {
        return true;
    }

    public boolean check() {
        return true;
    }

    public RunnerResult getResult() {
        return null;
    }

    public void startProcess() throws RunProcessException{
       runSeleniumHub();
       runSeleniumNode();
    }

    public void onFail() {
        stopServer();
    }

    public void cleanUp() {
        stopServer();
    }

    private void stopServer(){
        if (process != null){
            Logger.info("Terminating selenium server...");
            CliUtils.terminate(process);
        }
    }

    private String getSeleniumServerPath(){
        //TODO: handle any version in resources path here
        return DEFAULT_SELENIUM_PATH + String.format(DEFAULT_SELENIUM_SERVER_JAR_PATTERN, "3.9.1");
    }

    private void runSeleniumHub() throws RunProcessException {
        String filePath = getSeleniumServerPath();
        Logger.info("Running selenium server by path: " + filePath);
        File file = new File(filePath);
        try {
            //@link <a href="https://stackoverflow.com/questions/9884804/how-to-start-selenium-browser-with-proxy"/>
            process = CliUtils.runJarFile(file, "-port", "4444", "-role", "hub");
        } catch (IOException e) {
            throw new RunProcessException(e.getMessage());
        }
    }

    private void runSeleniumNode() throws RunProcessException {
        //https://www.guru99.com/introduction-to-selenium-grid.html
        String filePath = getSeleniumServerPath();
        Logger.info("Running selenium node by path: " + filePath);
        File file = new File(filePath);
        String chromeDriverPath = new File(DEFAULT_SELENIUM_DRIVERS_PATH+ DEFAULT_CHROME_DRIVER_FILENAME).getAbsolutePath();
        try {
            //@link <a href="https://stackoverflow.com/questions/9884804/how-to-start-selenium-browser-with-proxy"/>
            //-browser "browserName=chrome,maxinstance=1,platform=WINDOWS" -Dwebdriver.chrome.driver=C:\Selenium\chromedriver.exe
            process = CliUtils.runJarFile(file, "-role", "webdriver", "-hub", "http://localhost:4444/grid/register", "-port", "5556",
                    "-browser", "browserName=chrome,maxinstance=1,platform=WINDOWS",
                    "webdriver.chrome.driver="+chromeDriverPath);
        } catch (IOException e) {
            throw new RunProcessException(e.getMessage());
        }

        //java -jar selenium-server-standalone-3.9.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=firefox
    }

}
