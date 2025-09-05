package br.com.sunnypay.order;

import br.com.sunnypay.config.BaseStepDefinitions;
import br.com.sunnypay.shared.exception.ErrorResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import static br.com.sunnypay.order.OrderMock.missingRequiredFields;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderStepDefinitions extends BaseStepDefinitions {
    @Given("the application sends incomplete order data")
    public void given_TheApplicationSendsIncompleteOrderData() {
        clearAll();
        setRequestBody(missingRequiredFields());
    }

    @When("the application calls method {string} with URI {string}")
    public void when_TheApplicationCallsMethodWithUri(String method, String uri) {
        switch (method) {
            case "POST" -> executePost(uri);
            case "GET" -> executeGet(uri);
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
