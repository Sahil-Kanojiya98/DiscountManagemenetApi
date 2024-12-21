package com.hello.DiscountManagemenetAppApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class
Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String title;

    private String description;

    private Double mrp;

    private Double currentPrice;

    private Double discount;

    private Integer inventoryCount;

    @JsonIgnore
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductPriceHistory> productPriceHistoryList=new ArrayList<>();

}
