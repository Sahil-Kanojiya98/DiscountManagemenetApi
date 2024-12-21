package com.hello.DiscountManagemenetAppApi.service;


import com.hello.DiscountManagemenetAppApi.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public List<Product> addProduct(List<Product> productList);
    public Page<Product> getProduct(int pageNumber,int pageSize);
    public Product updateProductByStartDate(Double discount,Double mrp,Integer pid);
    public Product updateProductByEndDate(Double discount,Double mrp,Integer pid);
    public Product findProductById(Integer pid);
    public void updateProduct(Product product);

}
