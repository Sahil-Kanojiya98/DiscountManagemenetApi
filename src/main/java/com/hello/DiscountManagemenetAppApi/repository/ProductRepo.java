package com.hello.DiscountManagemenetAppApi.repository;

import com.hello.DiscountManagemenetAppApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("UPDATE Product SET discount=:discount,mrp=:mrp WHERE productId=:pid")
    Product updateByDiscountAndMrp(@Param("discount") Double discount, @Param("mrp") Double mrp, @Param("pid") Integer pid);

    @Query("UPDATE Product SET discount=:discount,mrp=:mrp WHERE productId=:pid")
    Product updateProductByEndDate(@Param("discount") Double discount,@Param("mrp") Double mrp,@Param("pid") Integer pid);

}
