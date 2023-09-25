package dev.lucas.preview.service;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    final SnsTemplate snsTemplate;

    private final String TOPIC_ARN = "arn:aws:sns:us-east-1:137454683450:AWS-ADA-SNS.fifo";

    public NotificationService(SnsTemplate snsTemplate) {
        this.snsTemplate = snsTemplate;
    }

    public void enviarNotificacaoDePostagem(String postagem) {
        snsTemplate.sendNotification(postagem, TOPIC_ARN);
        System.out.println("Notificação enviada com sucesso!");
    }
}
