CREATE TABLE IF NOT EXISTS customer_payment_credentials_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    api_key VARCHAR(255) NOT NULL UNIQUE,
    provider VARCHAR(50) NOT NULL,
    access_token VARCHAR(500) NOT NULL,
    public_key VARCHAR(500),
    pix_fee_percentage DECIMAL(5,2),
    credit_card_fee_percentage DECIMAL(5,2),
    debit_card_fee_percentage DECIMAL(5,2),
    webhook_url VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_id VARCHAR(36) NOT NULL UNIQUE, -- UUID como VARCHAR
    amount_in_cents BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO customer_payment_credentials_tb (
    api_key,
    provider,
    access_token,
    public_key,
    pix_fee_percentage,
    credit_card_fee_percentage,
    webhook_url,
    active
) VALUES (
             'sk_test_sunnypay_2705650973',
             'MERCADO_PAGO',
             'blablablabla',
             'blablablabla',
             0.99,
             3.49,
             'https://sua-api.com/webhooks/mercadopago',
             TRUE
);