package com.github.gomestkd.backend_desafio_picpay.notification;

import org.springframework.stereotype.Service;

import com.github.gomestkd.backend_desafio_picpay.transaction.Transaction;

@Service
public class NotificationService {
    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public void notifyTransaction(Transaction transaction) {
        notificationProducer.sendNotification(transaction);
    }
}
