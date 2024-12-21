package com.hello.DiscountManagemenetAppApi.controllers;

import com.hello.DiscountManagemenetAppApi.model.Product;
import com.hello.DiscountManagemenetAppApi.model.ProductPriceHistory;
import com.hello.DiscountManagemenetAppApi.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProduct(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        Page<Product> product = productService.getProduct(page-1, pageSize);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add-products")
    public ResponseEntity<List<Product>> addProduct(@RequestBody List<Product> productList){
        List<Product> addProductList = productService.addProduct(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductList);
    }

    @GetMapping("/find/{pid}")
    public ResponseEntity<List<ProductPriceHistory>> getProduct(@PathVariable("pid") Integer pid){
        List<ProductPriceHistory> productPriceHistories = productService.find(pid);
        return ResponseEntity.ok(productPriceHistories);
    }

}
