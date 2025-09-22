package br.com.sunnypay.shared.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "business-logic.error.messages")
@Component
@Getter
@Setter
public class BusinessLogicErrorMessages {
    private String incompatibleTotalAmounts;
}
