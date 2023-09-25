package dev.lucas.preview;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.lucas.preview.service.NotificationService;
import io.awspring.cloud.sns.core.SnsTemplate;
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

    final
    SnsTemplate snsTemplate;

    public PreviewApplication(SnsTemplate snsTemplate) {
        this.snsTemplate = snsTemplate;
    }

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
    public void listen(String message) throws JsonProcessingException {
        System.out.println("Mensagem recebida, encaminhando ao sistema de notificações...");

        NotificationService notificationService = new NotificationService(snsTemplate);
        notificationService.enviarNotificacaoDePostagem(message);
    }


}
