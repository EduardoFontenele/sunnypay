package br.com.sunnypay.unit.order;

import br.com.sunnypay.bdd.order.OrderMock;
import br.com.sunnypay.order.OrderController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
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
}
