package example;

import com.ufo.core.utils.common.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FOG on 12.02.2018.
 */
public class SimpleTestWithSeleniumGridTest extends TestBase {

    private static final String browserType = "chrome";

    @Test(testName = "Test to check that passed test results saved", description = "Always passed test description")
    public void alwaysPassTest() {
        Assert.assertTrue(true);
    }

    @Test(testName = "Test to check that failed test results saved", description = "Always failes test description")
    public void alwaysFailsTest() {
        Assert.fail("Failed on purpose");
    }

    @Test(testName = "Test to check that skipped test results saved", description = "Always skipped test description")
    public void alwaysSkippedTest() {
        throw new SkipException("Skipped on purpose");
    }

    @Test
    public void notClassifiedTest() {
        Assert.assertTrue(true);
    }

    @Test(testName = "Test to check work with grid", description = "Some test description, test probably fails")
    public void simpleTest() throws MalformedURLException {
        WaitUtils.wait(5);
        //http://www.softwaretestinghelp.com/selenium-grid-selenium-tutorial-29/
        DesiredCapabilities dr = null;
        if (browserType.equals("firefox")) {
            dr = DesiredCapabilities.firefox();
            dr.setBrowserName("firefox");
            dr.setPlatform(Platform.WINDOWS);
        }

        if (browserType.equals("ie")) {
            dr = DesiredCapabilities.internetExplorer();
            dr.setBrowserName("iexplore");
            dr.setPlatform(Platform.WINDOWS);
        }

        if (browserType.equals("chrome")) {
            dr = DesiredCapabilities.chrome();
            dr.setBrowserName("chrome");
            dr.setPlatform(Platform.WINDOWS);
        }

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dr);
        driver.navigate().to("http://gmail.com");
        //won't find anything here
        driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("username");
        driver.findElement(By.xpath("//input[@id='Passwd']")).sendKeys("password");
        driver.close();
    }

}
