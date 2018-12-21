package br.ufsc.cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions; import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "br.ufsc.cucumber.steps", features = "src/test/java/features/", monochrome = true)
public class TestRunner {
}

