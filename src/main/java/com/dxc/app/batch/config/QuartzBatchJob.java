package com.dxc.app.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuartzBatchJob implements org.quartz.Job {
    private Logger logger= LoggerFactory.getLogger(JobListener.class);
    @Value("${file.output:src/main/resources/}")
    private String outputPath;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

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