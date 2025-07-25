CREATE TABLE cart_item(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL ,
    product_id BIGINT NOT NULL ,
    quantity INT NOT NULL ,
    price BIGINT NOT NULL ,
    total_price BIGINT NOT NULL ,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' ,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)