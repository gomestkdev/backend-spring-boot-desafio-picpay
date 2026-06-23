package com.github.gomestkd.backend_desafio_picpay.transaction;

public class UnauthorizedTransaction extends RuntimeException {
    public UnauthorizedTransaction(String message) {
        super(message);
    }

}
