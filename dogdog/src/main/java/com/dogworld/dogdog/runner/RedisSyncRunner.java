package com.dogworld.dogdog.runner;

import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import com.dogworld.dogdog.product.infrastructure.ProductRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisSyncRunner implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final ProductRedisRepository productRedisRepository;

    @Override
    public void run(ApplicationArguments args){
        List<Product> products = productRepository.findAll();

        for(Product product : products){
            productRedisRepository.addProductToPriceSortedSet(product.getId(), product.getPrice());

            productRedisRepository.addProductToRatingSortedSet(product.getId(), product.getRatingAverage());

            productRedisRepository.addProductToCreatedAtSortedSet(product.getId(), product.getCreatedAt());
        }
    }
}
