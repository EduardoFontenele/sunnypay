package br.com.sunnypay.payment.domain;

import br.com.sunnypay.shared.domain.PaymentProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_payment_credentials_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPaymentCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String apiKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentProvider provider;

    @Column(nullable = false)
    private String accessToken;

    private String publicKey;

    @Column(precision = 5, scale = 2)
    private BigDecimal pixFeePercentage;

    @Column(precision = 5, scale = 2)
    private BigDecimal creditCardFeePercentage;

    @Column(precision = 5, scale = 2)
    private BigDecimal debitCardFeePercentage;

    private String webhookUrl;

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
