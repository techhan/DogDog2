DELIMITER $$

CREATE PROCEDURE rename_purchase_product_if_exists()
BEGIN
    DECLARE table_exists INT DEFAULT 0;
SELECT COUNT(*) INTO table_exists
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'purchase_product';

IF table_exists = 1 THEN
        RENAME TABLE purchase_product TO purchase_item;
END IF;
END$$

DELIMITER ;

CALL rename_purchase_product_if_exists();

DROP PROCEDURE rename_purchase_product_if_exists;