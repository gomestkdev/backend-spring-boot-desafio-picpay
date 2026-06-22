package com.github.gomestkd.backend_desafio_picpay.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.github.gomestkd.backend_desafio_picpay.exception.UnauthorizedTransaction;
import com.github.gomestkd.backend_desafio_picpay.transaction.Transaction;

@Service
public class AuthorizeService {
    private final RestClient restClient;
    
    public AuthorizeService(RestClient.Builder builder) {
        this.restClient = builder
            .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
            .build();
    }

    public void authorizeTransaction(Transaction transaction) {
        var response = restClient.get().retrieve().toEntity(Authorization.class);
        
        if(response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
            throw new UnauthorizedTransaction("Unauthorized transaction: %s".formatted(transaction));
        }
    }
}
