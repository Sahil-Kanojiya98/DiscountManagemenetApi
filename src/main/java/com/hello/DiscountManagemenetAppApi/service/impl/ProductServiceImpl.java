package com.hello.DiscountManagemenetAppApi.service.impl;

import com.hello.DiscountManagemenetAppApi.model.Product;
import com.hello.DiscountManagemenetAppApi.model.ProductPriceHistory;
import com.hello.DiscountManagemenetAppApi.repository.ProductPriceHistoryRepo;
import com.hello.DiscountManagemenetAppApi.repository.ProductRepo;
import com.hello.DiscountManagemenetAppApi.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductPriceHistoryRepo productPriceHistoryRepo;

    @Override
    @Transactional
    public List<Product> addProduct(List<Product> productList) {
        productList.stream().forEach((product) -> {
            ProductPriceHistory productPriceHistory = new ProductPriceHistory();
            productPriceHistory.setTimeStamp(LocalDateTime.now());
            productPriceHistory.setProductPrice(product.getMrp());
            productPriceHistory.setProduct(product);
            product.getProductPriceHistoryList().add(productPriceHistory);
            productPriceHistoryRepo.save(productPriceHistory);
            productRepo.save(product);
        });
        return productRepo.findAll();
    }

    @Override
    public Page<Product> getProduct(int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return productRepo.findAll(pageable);
    }

    @Override
    public Product updateProductByStartDate(Double discount,Double mrp, Integer pid) {
        return productRepo.updateByDiscountAndMrp(discount,mrp,pid);
    }

    @Override
    public Product updateProductByEndDate(Double discount, Double mrp, Integer pid) {
        return productRepo.updateProductByEndDate(discount,mrp,pid);
    }

    @Override
    public Product findProductById(Integer pid) {
        return productRepo.findById(pid).orElse(null);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        ProductPriceHistory productPriceHistory = new ProductPriceHistory();
        productPriceHistory.setProduct(product);
        productPriceHistory.setTimeStamp(LocalDateTime.now());
        productPriceHistory.setProductPrice(product.getCurrentPrice());
        product.getProductPriceHistoryList().add(productPriceHistory);
        productPriceHistoryRepo.save(productPriceHistory);
        productRepo.save(product);
    }

    public List<ProductPriceHistory> find(Integer pid){
        Product product = findProductById(pid);
        return product.getProductPriceHistoryList();
    }

}
