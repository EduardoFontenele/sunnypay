package br.com.sunnypay.bdd.order;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/bdd",
        glue = {"br.com.sunnypay.bdd.order", "br.com.sunnypay.bdd.config"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class OrderTest {
}
