package com.dogworld.dogdog.product.infrastructure;

import com.dogworld.dogdog.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRedisRepository {

    private final JedisPool jedisPool;

    private static final String PRICE_ZSET_KEY = "products:price";
    private static final String RATING_ZSET_KEY = "products:rating";
    private static final String CREATED_ZSET_KEY = "products:created";

    public void createdProductAddSortedSet(Product product) {
        addProductToPriceSortedSet(product.getId(), product.getPrice());
        addProductToRatingSortedSet(product.getId(), product.getRatingAverage());
        addProductToCreatedAtSortedSet(product.getId(), product.getCreatedAt());
    }

    public void removeProductSortedSet(Long productId) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.zrem(PRICE_ZSET_KEY, String.valueOf(productId));
            jedis.zrem(RATING_ZSET_KEY, String.valueOf(productId));
            jedis.zrem(CREATED_ZSET_KEY, String.valueOf(productId));
        }
    }

    public void addProductToPriceSortedSet(Long productId, BigDecimal price) {
        try(Jedis jedis = jedisPool.getResource()) {
            double doublePrice = price.doubleValue();
            jedis.zadd(PRICE_ZSET_KEY, doublePrice, productId.toString());
            log.info("price SortedSet Key : {}, score : {}", productId, doublePrice);
        }
    }

    public void addProductToRatingSortedSet(Long productId, BigDecimal rating) {
        try(Jedis jedis = jedisPool.getResource()) {
            double ratingValue = rating.doubleValue();
            jedis.zadd(RATING_ZSET_KEY, ratingValue, productId.toString());
            log.info("rating SortedSet Key : {}, score : {}", productId, ratingValue);
        }
    }

    public void addProductToCreatedAtSortedSet(Long productId, LocalDateTime createdAt) {
        try(Jedis jedis = jedisPool.getResource()) {
            long epochSecond = createdAt.toEpochSecond(ZoneOffset.UTC);
            jedis.zadd(CREATED_ZSET_KEY, epochSecond, productId.toString());
            log.info("created SortedSet Key : {}, score : {}", productId, epochSecond);
        }
    }
}
