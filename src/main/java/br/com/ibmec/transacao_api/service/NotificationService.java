package br.com.ibmec.transacao_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    @Autowired
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTransactionNotification(String toEmail, Long transactionId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Notificação de Transação");
        message.setText("Sua transação com ID " + transactionId + " foi realizada com sucesso.");
        mailSender.send(message);
    }
}
