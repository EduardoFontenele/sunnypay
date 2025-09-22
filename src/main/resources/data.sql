-- Criar tabela client_payment_config_tb
CREATE TABLE IF NOT EXISTS client_payment_config_tb (
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

-- Criar tabela orders_tb
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

-- Inserir dados de teste para client_payment_config_tb
INSERT INTO client_payment_config_tb (
    api_key,
    provider,
    access_token,
    public_key,
    pix_fee_percentage,
    credit_card_fee_percentage,
    webhook_url,
    active
) VALUES (
    'sk_test_abc123def456ghi789',
    'MERCADO_PAGO',
    'TEST-1234567890-112233-abcdef123456789-987654321',
    'TEST-pub-1234567890-112233',
    0.99,
    3.49,
    'https://webhook.site/12345678-1234-1234-1234-123456789012',
    TRUE
);