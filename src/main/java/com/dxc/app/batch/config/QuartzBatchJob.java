package com.dxc.app.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuartzBatchJob implements org.quartz.Job {
    private Logger logger= LoggerFactory.getLogger(QuartzBatchJob.class);
    @Value("${file.output}")
    private String outputPath;

    private final JobLauncher jobLauncher;

    private final Job job;

    public QuartzBatchJob(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Override
    public void execute(org.quartz.JobExecutionContext context) {
       final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
       final String filePath = outputPath+"report_" + timestamp + ".csv";


        try {
                logger.info("dbToCSV job running...");
                jobLauncher.run(job, new JobParametersBuilder()
                        .addString("job","job1")
                        .addString("report.path",filePath)
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }