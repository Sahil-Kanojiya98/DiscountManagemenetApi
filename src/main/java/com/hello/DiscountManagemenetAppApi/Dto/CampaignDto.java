package com.hello.DiscountManagemenetAppApi.Dto;

import com.hello.DiscountManagemenetAppApi.model.CampaignDiscount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignDto {

    private Integer campaignId;
    private String startDate;
    private String endDate;
    private String title;
    private List<CampaignDiscount> campaignDiscountList=new ArrayList<>();

}
