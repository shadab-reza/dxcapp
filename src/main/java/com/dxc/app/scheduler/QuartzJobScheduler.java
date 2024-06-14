package com.dxc.app.scheduler;

import com.dxc.app.batch.config.QuartzBatchJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzJobScheduler {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzBatchJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .withIdentity("quartzCronTrigger")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(60) // Run every 60 seconds
                        .repeatForever())
//              .withSchedule(CronScheduleBuilder.cronSchedule("0/10 0/1 * 1/1 * ? *")) //for every 10 seconds
//              .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ? *")) //for every seconds
                .forJob(jobDetail)
                .build();
    }

}
