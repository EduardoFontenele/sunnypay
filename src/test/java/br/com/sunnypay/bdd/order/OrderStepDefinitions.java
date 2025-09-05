package br.com.sunnypay.bdd.order;

import br.com.sunnypay.bdd.config.BaseStepDefinitions;
import br.com.sunnypay.shared.exception.ErrorResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderStepDefinitions extends BaseStepDefinitions {
    @Given("the application sends incomplete order data")
    public void given_TheApplicationSendsIncompleteOrderData() {
        clearAll();
        setRequestBody(OrderMock.missingRequiredFields());
        setupJsonHeaders();
    }

    @When("the application calls method {string} with URI {string}")
    public void when_TheApplicationCallsMethodWithUri(String method, String uri) {
        switch (method) {
            case "POST" -> executePost(uri).then().log().body();
            case "GET" -> executeGet(uri).then().log().body();
        }
    }

    @Then("should respond with status {int}")
    public void then_ShouldRespondWithStatus(int status) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @And("should return error response with validation errors")
    public void and_ShouldReturnErrorResponseWithValidationErrors() {
        var errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.errors()).isNotEmpty();
        assertThat(errorResponse.message()).isEqualTo("Validation failed");
    }
}
