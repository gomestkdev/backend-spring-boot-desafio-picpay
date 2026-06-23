package com.github.gomestkd.backend_desafio_picpay.transaction;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.gomestkd.backend_desafio_picpay.authorization.AuthorizeService;
import com.github.gomestkd.backend_desafio_picpay.notification.NotificationService;
import com.github.gomestkd.backend_desafio_picpay.wallet.Wallet;
import com.github.gomestkd.backend_desafio_picpay.wallet.WalletRepository;
import com.github.gomestkd.backend_desafio_picpay.wallet.WalletType;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizeService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(
        TransactionRepository transactionRepository, 
        WalletRepository walletRepository, 
        AuthorizeService authorizationService, 
        NotificationService notificationService
    ) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        // Validate transaction
        validateTransaction(transaction);

        // Save transaction and update wallet balance
        var transactionSaved = transactionRepository.save(transaction);

        // Update payer's wallet balance - Debit the amount from the payer's wallet
        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();
        
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));

        // Authorization transaction
        authorizationService.authorizeTransaction(transactionSaved);

        // notification
        notificationService.notifyTransaction(transactionSaved);
        return transactionSaved;
    }

    private void validateTransaction(Transaction transaction) {
        walletRepository.findById(transaction.payee())
            .map(
                payee -> walletRepository.findById(transaction.payer())
                    .map(payer -> isTransactionValid(transaction, payer) ? transaction: null)
                        .orElseThrow(
                            () -> new InvalidTransactionException("Transaction is not valid: %s".formatted(transaction))
                        )
            )
                .orElseThrow(
                    () -> new InvalidTransactionException("Transaction is not valid: %s".formatted(transaction))
                );
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() &&
        payer.balance().compareTo(transaction.value()) >= 0 &&
        !payer.id().equals(transaction.payee());
    }
}
