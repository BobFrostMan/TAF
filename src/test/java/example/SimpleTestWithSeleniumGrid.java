package example;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FOG on 12.02.2018.
 */
public class SimpleTestWithSeleniumGrid extends TestBase {

    private static final String browserType = "firefox";

    @Test
    public void simpleTest() throws MalformedURLException {
        //http://www.softwaretestinghelp.com/selenium-grid-selenium-tutorial-29/
        DesiredCapabilities dr=null;
        if(browserType.equals("firefox")){
            dr=DesiredCapabilities.firefox();
            dr.setBrowserName("firefox");
            dr.setPlatform(Platform.WINDOWS);
        }else{
            dr=DesiredCapabilities.internetExplorer();
            dr.setBrowserName("iexplore");
            dr.setPlatform(Platform.WINDOWS);
        }
        RemoteWebDriver driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dr);
        driver.navigate().to("http://gmail.com");
        driver.findElement(By.xpath("//input[@id='Email']")) .sendKeys("username");
        driver.findElement(By.xpath("//input[@id='Passwd']")) .sendKeys("password");
        driver.close();
    }


}
