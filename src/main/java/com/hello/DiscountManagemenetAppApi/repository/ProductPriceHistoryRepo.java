package com.hello.DiscountManagemenetAppApi.repository;

import com.hello.DiscountManagemenetAppApi.model.Product;
import com.hello.DiscountManagemenetAppApi.model.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceHistoryRepo extends JpaRepository<ProductPriceHistory,Integer> {

    @Query("SELECT p FROM ProductPriceHistory p WHERE p.product=:product")
    ProductPriceHistory findByProduct(Product product);

}
