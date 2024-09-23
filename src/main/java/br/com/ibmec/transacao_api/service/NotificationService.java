package br.com.ibmec.transacao_api.service;

public interface NotificationService {
    void sendTransactionNotification(String toEmail, Long transactionId);
}
