package com.hello.DiscountManagemenetAppApi.service.scheduler;

import com.hello.DiscountManagemenetAppApi.service.CampaignService;
import com.hello.DiscountManagemenetAppApi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExecuteTask {

    Logger logger= LoggerFactory.getLogger(ExecuteTask.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("job1")
    private Job job1;

    @Autowired
    @Qualifier("job2")
    private Job job2;

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private ProductService productService;

//    @Scheduled(cron = "0 0 0 * * *")   //every 24 hour
    @Scheduled(fixedDelay = 30000)       //every 30 seconds
    public void updateProductByStartDate() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job1, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed to start.");
        }

//        System.out.println("start by startdate");
//        List<Campaign> campaigns = campaignService.getCampaignBasedOnDate(LocalDate.now());
//        for (Campaign campaign : campaigns) {
//            for (CampaignDiscount campaignDiscount : campaign.getCampaignDiscountList()) {
//                Product product = productService.findProductById(campaignDiscount.getProductId());
//                product.setDiscount(product.getDiscount() + campaignDiscount.getDiscount());
//                product.setCurrentPrice(product.getCurrentPrice() - ((campaignDiscount.getDiscount() * product.getMrp()) / 100));
//                productService.updateProduct(product);
//            }
//        }
//        System.out.println("end by startdate");

    }

//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedDelay = 30000)
    public void updateProductByEndDate() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job2, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed to start.");
        }

//        System.out.println("start by enddate");
//        List<Campaign> campaigns = campaignService.getCampaignBasedOnEndDate(LocalDate.now());
//        for (Campaign campaign : campaigns) {
//            for (CampaignDiscount campaignDiscount : campaign.getCampaignDiscountList()) {
//                Product product = productService.findProductById(campaignDiscount.getProductId());
//                product.setDiscount(product.getDiscount() - campaignDiscount.getDiscount());
//                product.setCurrentPrice(product.getCurrentPrice() + ((campaignDiscount.getDiscount() * product.getMrp()) / 100));
//                productService.updateProduct(product);
//                System.out.println("ending ---"+product);
//            }
//        }
//        System.out.println("end by enddate");

    }

}
