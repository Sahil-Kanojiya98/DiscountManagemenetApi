package com.hello.DiscountManagemenetAppApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer campaignDiscountId;

    private Double Discount;

    private Integer productId;

}
