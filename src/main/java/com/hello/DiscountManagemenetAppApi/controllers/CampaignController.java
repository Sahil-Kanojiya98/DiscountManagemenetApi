package com.hello.DiscountManagemenetAppApi.controllers;

import com.hello.DiscountManagemenetAppApi.Dto.CampaignDto;
import com.hello.DiscountManagemenetAppApi.model.Campaign;
import com.hello.DiscountManagemenetAppApi.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class CampaignController {

    Logger logger= LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private JobLauncher jobLauncher;

    @PostMapping("/add-campaign")
    public ResponseEntity<Campaign> saveCampaign(@RequestBody CampaignDto campaignDto){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate=LocalDate.parse(campaignDto.getStartDate(),formatter);
        LocalDate endDate=LocalDate.parse(campaignDto.getEndDate(),formatter);
        Campaign campaign=new Campaign();
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
        campaign.setTitle(campaignDto.getTitle());
        campaign.setCampaignDiscountList(campaignDto.getCampaignDiscountList());
        Campaign addedCampaign = campaignService.addCampaign(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCampaign);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Campaign>> getCampaignToday(){
        List<Campaign> campaignBasedOnDate = campaignService.getCampaignBasedOnDate(LocalDate.now());
        return ResponseEntity.ok(campaignBasedOnDate);
    }

}
