package com.dxc.app.batch.config;

import com.dxc.app.model.CustomerEntity;
import com.dxc.app.model.CustomerModel;
import com.dxc.app.batch.processor.CustomerItemProcessor;
import com.dxc.app.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;


@Configuration
@EnableBatchProcessing
public class BatchConfig {


	private final CustomerRepository customerRepository;

    public BatchConfig(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Bean
	public RepositoryItemReader<CustomerEntity> reader() {
		RepositoryItemReader<CustomerEntity> reader = new RepositoryItemReader<>();
		reader.setRepository(customerRepository);
		reader.setMethodName("findAll");
		reader.setPageSize(100);
		HashMap<String, Sort.Direction> sorts = new HashMap<>();
		sorts.put("sno", Sort.Direction.ASC);
		reader.setSort(sorts);
		return reader;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<CustomerModel> writer(@Value("#{jobParameters['report.path']}") String outputPath) {
		FlatFileItemWriter<CustomerModel> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource(outputPath));
		DelimitedLineAggregator<CustomerModel> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<CustomerModel> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[]{"sno", "fname", "lname", "totalSpend"});
		lineAggregator.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(lineAggregator);
		return writer;
	}

	// Custom processor for process incoming rows from reader service
	@Bean
	public CustomerItemProcessor processor() {
		return new CustomerItemProcessor();
	}

	// Step bean for the batch processing
	@Bean
	public Step databaseToCSVImport(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<CustomerEntity> reader
	,ItemWriter<CustomerModel> writer) {
		return new StepBuilder("dbToCsvStep", jobRepository)
				.<CustomerEntity, CustomerModel> chunk(10, transactionManager)
				.reader(reader)
				.processor(processor())
				.writer(writer)
				.build();
	}

	// Job bean for running the batch job for given step
	@Bean("job")
	public Job importCustomersJob(JobRepository jobRepository, JobListener listener, Step step1) {
		return new JobBuilder("dbToCsvBatchJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}


}

