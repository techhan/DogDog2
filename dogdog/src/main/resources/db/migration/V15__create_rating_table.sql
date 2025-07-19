CREATE TABLE rating (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      product_id BIGINT NOT NULL,
      member_id BIGINT NOT NULL,
      score DECIMAL(2,1) NOT NULL,
      comment VARCHAR(1000),
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      is_deleted BOOLEAN DEFAULT FALSE,
     UNIQUE KEY uq_rating_product_member (product_id, member_id)
)
