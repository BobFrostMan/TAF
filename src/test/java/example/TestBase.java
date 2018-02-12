package example;

import com.ufo.core.exception.RunProcessException;
import com.ufo.core.runner.IRunner;
import com.ufo.core.runner.impl.SeleniumGridRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FOG on 12.02.2018.
 */
public class TestBase {

    protected List<IRunner> runners;

    public TestBase(){
        runners = new ArrayList<IRunner>();
        runners.add(new SeleniumGridRunner());
    }

    @BeforeSuite
    public void setUp() throws RunProcessException {
        for (IRunner runner: runners){
            runner.run();
        }
    }

    @AfterSuite
    public void cleanUp(){
        for (IRunner runner: runners){
            runner.cleanUp();
        }
    }
}
