package com.hello.DiscountManagemenetAppApi.repository;

import com.hello.DiscountManagemenetAppApi.model.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign,Integer> {

    @Query("SELECT c FROM Campaign c WHERE startDate=:date")
    List<Campaign> getTodayCampaign(LocalDate date);

    @Query("SELECT c FROM Campaign c WHERE endDate=:date")
    List<Campaign> getEndCampaign(LocalDate date);

    Page<Campaign> findByStartDate(LocalDate date, PageRequest pageRequest);

    Page<Campaign> findByEndDate(LocalDate date,PageRequest pageRequest);

}
