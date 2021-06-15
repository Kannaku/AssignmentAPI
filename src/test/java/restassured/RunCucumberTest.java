package restassured;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},glue="restassured",features= {"src/test/resources/features/restassuredtest.feature"})
public class RunCucumberTest {

}
