package example;

import com.ufo.core.exception.PreparationFailedException;
import com.ufo.core.prepare.IPrepare;
import com.ufo.core.prepare.impl.SeleniumGridRunner;
import com.ufo.core.prepare.impl.SeleniumNodeRunner;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by FOG on 12.02.2018.
 *
 * Simple test base example to check preparation runners
 */
public class TestBase {

    protected List<IPrepare> prepareRunners;
    protected RemoteWebDriver driver;

    public TestBase(){
        prepareRunners = new ArrayList<IPrepare>();
        prepareRunners.add(new SeleniumGridRunner());
        prepareRunners.add(new SeleniumNodeRunner());
    }

    @BeforeSuite
    public void setUp() throws PreparationFailedException {
        for (IPrepare prepareRunner: prepareRunners){
            prepareRunner.run();
        }
    }

    @AfterSuite
    public void cleanUp(){
        if (driver != null){
            driver.quit();
        }
        for (IPrepare prepareRunner: prepareRunners){
            prepareRunner.cleanUp();
        }
    }

}
