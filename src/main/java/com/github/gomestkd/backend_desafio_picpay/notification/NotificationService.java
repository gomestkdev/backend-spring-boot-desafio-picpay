package com.github.gomestkd.backend_desafio_picpay.notification;

import org.springframework.stereotype.Service;

import com.github.gomestkd.backend_desafio_picpay.transaction.Transaction;

@Service
public class NotificationService {
    public void notifyTransaction(Transaction transaction) {
        
        System.out.println("Notification sent for transaction: " + transaction);
    }
}
