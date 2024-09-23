package br.com.ibmec.transacao_api.service;

import org.springframework.stereotype.Service;

@Service
public class MockNotificationService implements NotificationService {

    @Override
    public void sendTransactionNotification(String toEmail, Long transactionId) {
        // Simular envio de notificação sem realmente enviar
        System.out.println("Mock: Notificando transação " + transactionId + " para " + toEmail);
    }
}
