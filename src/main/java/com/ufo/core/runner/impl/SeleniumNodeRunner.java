package com.ufo.core.runner.impl;

import com.ufo.core.exception.RunProcessException;
import com.ufo.core.utils.common.CliUtils;
import com.ufo.core.utils.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * User: Maxim Zaverukha<br/>
 * Date: 12.02.2018<br/>
 * Time: 13:12<br/>
 *
 * Runner that start selenium slave node
 */
public class SeleniumNodeRunner extends SeleniumGridRunner {

    private static final String DEFAULT_DRIVER_BINARY_PATH = "src/main/resources/driver/bin/";
    private static final String DEFAULT_CHROME_DRIVER_FILENAME = "chromedriver.exe";

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void startProcess() throws RunProcessException{
        String filePath = getSeleniumServerPath();
        Logger.info("Running selenium node by path: " + filePath);
        File file = new File(filePath);
        String binFilePath = DEFAULT_DRIVER_BINARY_PATH + DEFAULT_CHROME_DRIVER_FILENAME;
        String chromeDriverPath = new File(binFilePath).getAbsolutePath();
        try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            //@link <a href="https://stackoverflow.com/questions/9884804/how-to-start-selenium-browser-with-proxy"/>
            //-browser "browserName=chrome,maxinstance=1,platform=WINDOWS" -Dwebdriver.chrome.driver=C:\Selenium\chromedriver.exe
            /*
                java -Dwebdriver.chrome.driver="../driver/bin/chromedriver.exe" -jar selenium-server-standalone-3.9.1.jar
                -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=chrome,maxinstance=1,platform=WINDOWS
             */
            CliUtils.execute(new File(DEFAULT_SELENIUM_PATH), "cmd.exe", "/c", new File(DEFAULT_SELENIUM_PATH + "start-selenium-node.bat").getAbsolutePath());
//            process = CliUtils.execute(System.getenv("JAVA_HOME") + "/bin/java",  "-jar", "-Dwebdriver.chrome.driver=\""+chromeDriverPath+"\"", file.getAbsolutePath(),
//                    "-role", "webdriver", "-hub", "http://localhost:4444/grid/register", "-port", "5556",
//                    "-browser", "browserName=chrome,version=ANY,platform=WINDOWS,maxInstances=5");
        } catch (IOException e) {
            throw new RunProcessException(e.getMessage());
        }

        //java -jar selenium-server-standalone-3.9.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=firefox
    }

    //-Dwebdriver.chrome.driver="../driver/bin/chromedriver.exe" -jar selenium-server-standalone-3.9.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=chrome,maxinstance=1,platform=WINDOWS
}
