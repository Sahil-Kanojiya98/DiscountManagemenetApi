package com.hello.DiscountManagemenetAppApi.service;



import com.hello.DiscountManagemenetAppApi.model.Campaign;

import java.time.LocalDate;
import java.util.List;

public interface CampaignService {

    public Campaign addCampaign(Campaign campaign);
    public List<Campaign> getCampaignBasedOnDate(LocalDate date);
    public List<Campaign> getCampaignBasedOnEndDate(LocalDate date);

}
