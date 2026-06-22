package com.github.gomestkd.backend_desafio_picpay.exception;

public class UnauthorizedTransaction extends RuntimeException {
    public UnauthorizedTransaction(String message) {
        super(message);
    }

}
