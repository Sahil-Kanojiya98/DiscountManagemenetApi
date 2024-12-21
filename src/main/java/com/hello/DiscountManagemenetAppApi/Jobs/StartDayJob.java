package com.hello.DiscountManagemenetAppApi.Jobs;

import com.hello.DiscountManagemenetAppApi.Jobs.Notifications.StartDayJobNotification;
import com.hello.DiscountManagemenetAppApi.model.Campaign;
import com.hello.DiscountManagemenetAppApi.model.CampaignDiscount;
import com.hello.DiscountManagemenetAppApi.model.Product;
import com.hello.DiscountManagemenetAppApi.repository.CampaignRepo;
import com.hello.DiscountManagemenetAppApi.repository.ProductRepo;
import com.hello.DiscountManagemenetAppApi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StartDayJob {

    private final StartDayJobNotification notification;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final CampaignRepo campaignRepo;
    private final ProductService productService;
    private final ProductRepo productRepo;

    private Logger logger= LoggerFactory.getLogger(StartDayJob.class);

    @Bean
    public ItemReader<? extends Campaign> reader(){
        RepositoryItemReader<Campaign> readerVar = new RepositoryItemReader<>();
        readerVar.setName("read1");
        readerVar.setMethodName("findByStartDate");
        readerVar.setPageSize(100);
        readerVar.setArguments(Collections.singletonList(LocalDate.now()));
        readerVar.setSort(Collections.singletonMap("campaignId", Sort.Direction.ASC));
        readerVar.setRepository(campaignRepo);
        return readerVar;
    }

    @Bean
    public ItemProcessor<? super Campaign, ? extends List<Product>> processor() {
        return new ItemProcessor<Campaign, List<Product>>() {
            @Override
            public List<Product> process(Campaign item) throws Exception {
                List<Product> updatedProductList = new ArrayList<>();
                logger.info("process {}", item.getCampaignDiscountList().size());
                for (CampaignDiscount campaignDiscount : item.getCampaignDiscountList()) {
                    Product product = productService.findProductById(campaignDiscount.getProductId());
                    product.setDiscount(product.getDiscount() + campaignDiscount.getDiscount());
                    product.setCurrentPrice(product.getCurrentPrice() - ((campaignDiscount.getDiscount() * product.getMrp()) / 100));
                    productService.updateProduct(product);
                }
                return updatedProductList;
            }
        };
    }

    @Bean
    public ItemWriter<? super List<Product>> writer(){
        return new ItemWriter<List<Product>>() {
            @Override
            public void write(Chunk<? extends List<Product>> chunk) throws Exception {
                for (List<Product> productList:chunk){
                    logger.info("{}", productList.size());
                    productRepo.saveAll(productList);
                }
            }
        };
    }

    @Bean
    public Step step() throws Exception {
        return new StepBuilder("jobstep", jobRepository)
                .<Campaign, List<Product>>chunk(25000, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(50); //50
        taskExecutor.setMaxPoolSize(200);   //200
        taskExecutor.setQueueCapacity(100);  //100
        taskExecutor.setThreadNamePrefix("AsyncTask-");
        taskExecutor.initialize();
        return taskExecutor;
    }

//    @Bean
//    public TaskExecutor taskExecutor(){
//        SimpleAsyncTaskExecutorBuilder builder=new SimpleAsyncTaskExecutorBuilder()
//                .concurrencyLimit(1000);
//        return builder.build();
//    }

    @Bean(name = "job1")
    public Job job() throws Exception {
        return new JobBuilder("tempJob1", jobRepository)
                .start(step())
                .listener(notification)
                .build();
    }

}