package com.hello.DiscountManagemenetAppApi.repository;

import com.hello.DiscountManagemenetAppApi.model.CampaignDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDiscountRepo extends JpaRepository<CampaignDiscount,Integer> { }