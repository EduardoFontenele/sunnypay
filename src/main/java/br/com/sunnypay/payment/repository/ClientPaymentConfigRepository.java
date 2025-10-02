package br.com.sunnypay.payment.repository;

import br.com.sunnypay.payment.domain.CustomerPaymentCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientPaymentConfigRepository extends JpaRepository<CustomerPaymentCredentials, Long> {
    Optional<CustomerPaymentCredentials> findByApiKey(String apiKey);
}
