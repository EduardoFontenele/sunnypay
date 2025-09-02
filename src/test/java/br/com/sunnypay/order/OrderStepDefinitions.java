package br.com.sunnypay.order;

import br.com.sunnypay.config.BaseStepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderStepDefinitions extends BaseStepDefinitions {
    @Given("I test something")
    public void given_ITestSomething() {
        executePost("api/orders");
    }

    @Then("answer something")
    public void then_AnswerSomething() {
        assertEquals(200, response.statusCode());
    }
}
