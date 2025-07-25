CREATE TABLE refund (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_product_id BIGINT NOT NULL ,
    refund_amount BIGINT NOT NULL ,
    status VARCHAR(20) NOT NULL ,
    refund_type VARCHAR(20) NOT NULL,
    reason  TEXT,
    requested_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    processed_at DATETIME ,
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)