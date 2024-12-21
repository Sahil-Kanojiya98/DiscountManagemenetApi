package com.hello.DiscountManagemenetAppApi.service.impl;


import com.hello.DiscountManagemenetAppApi.model.Campaign;
import com.hello.DiscountManagemenetAppApi.repository.CampaignDiscountRepo;
import com.hello.DiscountManagemenetAppApi.repository.CampaignRepo;
import com.hello.DiscountManagemenetAppApi.service.CampaignService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepo campaignRepo;
    private final CampaignDiscountRepo campaignDiscountRepo;

    @Override
    public Campaign addCampaign(Campaign campaign) {
        return campaignRepo.save(campaign);
    }

    @Override
    public List<Campaign> getCampaignBasedOnDate(LocalDate date) {
        return campaignRepo.getTodayCampaign(date);
    }

    @Override
    public List<Campaign> getCampaignBasedOnEndDate(LocalDate date) {
        return campaignRepo.getEndCampaign(date);
    }

}
