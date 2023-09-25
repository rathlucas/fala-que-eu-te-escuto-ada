package dev.lucas.preview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    final SnsTemplate snsTemplate;

    final ObjectMapper objectMapper;

    public NotificationService(SnsTemplate snsTemplate, ObjectMapper objectMapper) {
        this.snsTemplate = snsTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarNotificacaoDePostagem(String postagem) throws JsonProcessingException {
        String TOPIC_ARN = "arn:aws:sns:us-east-1:137454683450:ADA-SNS";

        SnsNotification<String> notification = SnsNotification
                .builder(postagem)
                .build();

        snsTemplate.sendNotification(TOPIC_ARN, notification);
        System.out.println("Notificação enviada com sucesso!");
    }
}
