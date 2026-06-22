package com.github.gomestkd.backend_desafio_picpay.wallet;

public enum WalletType {
    COMUM(1),
    LOGISTA(2);

    private final int value;

    private WalletType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
