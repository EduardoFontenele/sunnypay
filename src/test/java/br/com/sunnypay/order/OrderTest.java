package br.com.sunnypay.order;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/bdd",
        glue = {"br.com.sunnypay.order", "br.com.sunnypay.config"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class OrderTest {
}
