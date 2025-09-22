package br.com.sunnypay.payment.repository;

import br.com.sunnypay.payment.domain.ClientPaymentConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientPaymentConfigRepository extends JpaRepository<ClientPaymentConfig, Long> {
    Optional<ClientPaymentConfig> findByApiKey(String apiKey);
}
