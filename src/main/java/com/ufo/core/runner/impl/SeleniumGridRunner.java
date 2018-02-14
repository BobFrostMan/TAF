package com.ufo.core.runner.impl;

import com.ufo.core.exception.RunProcessException;
import com.ufo.core.runner.AbstractRunner;
import com.ufo.core.runner.bean.RunnerResult;
import com.ufo.core.utils.common.CliUtils;
import com.ufo.core.utils.common.HttpUtils;
import com.ufo.core.utils.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by FOG on 12.02.2018.
 *
 * Runner that start selenium server, for tests execution in grid
 */
public class SeleniumGridRunner extends AbstractRunner{

    protected static final String DEFAULT_SELENIUM_PATH = "src/main/resources/grid/";
    protected static final String DEFAULT_SELENIUM_SERVER_JAR_PATTERN = "selenium-server-standalone-%s.jar";
    protected static final String DEFAULT_SELENIUM_SERVER_ENDPOINT = "http://localhost:4444/grid/console?config=true&configDebug=true";

    protected Process process;

    public boolean isFinished() {
        try {
            String content = HttpUtils.get(DEFAULT_SELENIUM_SERVER_ENDPOINT);
            return content.length() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean check() {
        return true;
    }

    public RunnerResult getResult() {
        return null;
    }

    public void startProcess() throws RunProcessException{
        String filePath = getSeleniumServerPath();
        Logger.info("Running selenium server by path: " + filePath);
        File file = new File(filePath);
        try {
            //@link <a href="https://stackoverflow.com/questions/9884804/how-to-start-selenium-browser-with-proxy"/>
            //java -jar selenium-server-standalone-3.9.1.jar -role hub
            //process = CliUtils.runJarFile(file);//, "-port", "4444", "-role", "hub");
            CliUtils.execute(new File(DEFAULT_SELENIUM_PATH), "cmd.exe", "/c", new File(DEFAULT_SELENIUM_PATH + "start-selenium-server.bat").getAbsolutePath());
        } catch (IOException e) {
            throw new RunProcessException(e.getMessage());
        }
    }

    public void onFail() {
        stopServer();
    }

    public void cleanUp() {
        stopServer();
    }

    private void stopServer(){
        if (process != null){
            Logger.info("Terminating selenium server process...");
            CliUtils.terminate(process);
            try {
                HttpUtils.get("http://localhost:4444/selenium-server/driver/?cmd=shutDownSeleniumServer");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected String getSeleniumServerPath(){
        //TODO: handle any version in resources path here
        return DEFAULT_SELENIUM_PATH + String.format(DEFAULT_SELENIUM_SERVER_JAR_PATTERN, "3.9.1");
    }

}
