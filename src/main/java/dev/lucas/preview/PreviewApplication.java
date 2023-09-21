package dev.lucas.preview;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class PreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreviewApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @SqsListener("SQS-REVIEW-ADA.fifo")
    public void listen(String message) {
        System.out.println(message);
    }


}
