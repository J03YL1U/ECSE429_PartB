package ECSE429_PartB;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@IncludeEngines("cucumber")
@SelectClasspathResource("ECSE429_PartB")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class RunCucumberTest {
}
