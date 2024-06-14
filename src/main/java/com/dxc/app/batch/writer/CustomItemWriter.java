package com.dxc.app.batch.writer;

import com.dxc.app.model.CustomerModel;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.stereotype.Component;

@Component
class CustomItemWriter implements ItemWriter<CustomerModel> {

    private final FlatFileItemWriter<CustomerModel> writer;

    public CustomItemWriter(FlatFileItemWriter<CustomerModel> writer) {
        this.writer = writer;
    }

    @BeforeStep
    public void openWriter(StepExecution stepExecution) throws Exception {
        writer.open(stepExecution.getExecutionContext());
    }

    @Override
    public void write(Chunk<? extends CustomerModel> chunk) throws Exception {
        writer.write((Chunk<? extends CustomerModel>) chunk.getItems());
    }

    @BeforeStep
    public void closeWriter(StepExecution stepExecution) throws Exception {
        writer.close();
    }
}
