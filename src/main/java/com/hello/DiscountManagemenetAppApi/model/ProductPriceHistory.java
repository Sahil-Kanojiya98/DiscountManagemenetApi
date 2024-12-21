package com.hello.DiscountManagemenetAppApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;

    private Double productPrice;

    private LocalDateTime timeStamp;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

}
