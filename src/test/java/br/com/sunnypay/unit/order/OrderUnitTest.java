package br.com.sunnypay.unit.order;

import br.com.sunnypay.bdd.order.OrderMock;
import br.com.sunnypay.order.OrderController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@DisplayName("Order Unit Tests")
public class OrderUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return validation errors when order has missing required fields")
    void shouldReturnValidationErrorsForInvalidOrder() throws Exception {
        var invalidOrderJson = objectMapper.writeValueAsString(OrderMock.missingRequiredFields());

        mockMvc.perform(post("/api/orders")
                        .content(invalidOrderJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors[?(@.field=='customer')].message").value("Customer is required"))
                .andExpect(jsonPath("$.errors[?(@.field=='referenceId')].message").value("Reference ID is required"))
                .andExpect(jsonPath("$.errors[?(@.field=='paymentMethod')].message").value("Payment method is required"))
                .andExpect(jsonPath("$.errors[?(@.field=='amountInCents')].message").value("Amount is required"))
                .andExpect(jsonPath("$.errors[?(@.field=='items')].message").value("Items are required"));
    }

    @Test
    @DisplayName("Should return validation error for negative amount")
    void shouldReturnValidationErrorForNegativeAmount() throws Exception {
        var order = OrderMock.withNegativeAmount();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='amountInCents')].message").value("Amount must be positive"));
    }

    @Test
    @DisplayName("Should return validation error for empty items list")
    void shouldReturnValidationErrorForEmptyItems() throws Exception {
        var order = OrderMock.withEmptyItems();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='items')].message").value("At least one item is required"));
    }

    @Test
    @DisplayName("Should return validation errors for invalid customer data")
    void shouldReturnValidationErrorsForInvalidCustomer() throws Exception {
        var order = OrderMock.withInvalidCustomer();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()").value(greaterThan(0)))
                .andExpect(jsonPath("$.errors[?(@.field=='customer.name')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.email')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.document')]").exists())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.phones')]").exists());
    }

    @Test
    @DisplayName("Should return validation error for invalid email")
    void shouldReturnValidationErrorForInvalidEmail() throws Exception {
        var order = OrderMock.withInvalidEmail();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.email')].message").value("Invalid email format"));
    }

    @Test
    @DisplayName("Should return validation error for invalid document format")
    void shouldReturnValidationErrorForInvalidDocument() throws Exception {
        var order = OrderMock.withInvalidDocument();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.document')].message").value("Document must be a valid CPF (11 digits) or CNPJ (14 digits)"));
    }

    @Test
    @DisplayName("Should return validation errors for invalid phone data")
    void shouldReturnValidationErrorsForInvalidPhone() throws Exception {
        var order = OrderMock.withInvalidPhone();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.phones[0].country')].message").value("Phone country must be in format +XX"))
                .andExpect(jsonPath("$.errors[?(@.field=='customer.phones[0].area')].message").value("Phone area must be 2 digits"))
                .andExpect(jsonPath("$.errors[?(@.field=='customer.phones[0].number')].message").value("Phone number must be 8 or 9 digits"))
                .andExpect(jsonPath("$.errors[?(@.field=='customer.phones[0].type')].message").value("Phone type is required"));
    }

    @Test
    @DisplayName("Should return validation errors for invalid item data")
    void shouldReturnValidationErrorsForInvalidItem() throws Exception {
        var order = OrderMock.withInvalidItem();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='items[0].name')].message").value("Item name is required"))
                .andExpect(jsonPath("$.errors[?(@.field=='items[0].quantity')].message").value("Item quantity must be positive"))
                .andExpect(jsonPath("$.errors[?(@.field=='items[0].unitAmount')].message").value("Item amount must be positive"));
    }

    @Test
    @DisplayName("Should return validation error for too long reference ID")
    void shouldReturnValidationErrorForTooLongReferenceId() throws Exception {
        var order = OrderMock.withTooLongReferenceId();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='referenceId')].message").value("Reference ID must not exceed 100 characters"));
    }

    @Test
    @DisplayName("Should return validation error for too many phones")
    void shouldReturnValidationErrorForTooManyPhones() throws Exception {
        var order = OrderMock.withTooManyPhones();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.phones')].message").value("Customer must have between 1 and 5 phones"));
    }

    @Test
    @DisplayName("Should return validation error for too long customer name")
    void shouldReturnValidationErrorForTooLongCustomerName() throws Exception {
        var order = OrderMock.withTooLongCustomerName();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='customer.name')].message").value("Customer name must not exceed 255 characters"));
    }

    @Test
    @DisplayName("Should return validation error for too long item name")
    void shouldReturnValidationErrorForTooLongItemName() throws Exception {
        var order = OrderMock.withTooLongItemName();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field=='items[0].name')].message").value("Item name must not exceed 255 characters"));
    }

    @Test
    @DisplayName("Should accept valid order")
    void shouldAcceptValidOrder() throws Exception {
        var validOrder = OrderMock.validOrder();

        mockMvc.perform(post("/api/orders")
                        .content(objectMapper.writeValueAsString(validOrder))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
