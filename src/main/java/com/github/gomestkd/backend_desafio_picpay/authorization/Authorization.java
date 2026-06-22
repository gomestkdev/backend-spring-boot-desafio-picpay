package com.github.gomestkd.backend_desafio_picpay.authorization;

public record Authorization(
    String message
) {
    public boolean isAuthorized() {
        return "Autorizado".equals(message);
    }
}
