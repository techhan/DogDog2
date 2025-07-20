
CREATE TABLE coupons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    discount_type VARCHAR(20) NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    min_order_amount DECIMAL(10,2) ,
    max_discount_amount DECIMAL(10,2) ,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    usage_limit INT ,
    issue_count INT NOT NULL ,
    used_count INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)