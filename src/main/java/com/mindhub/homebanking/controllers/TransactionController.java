package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;


    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<?> newTransfer(
            @RequestParam Double amount, @RequestParam String description, @RequestParam String sourceAccountNumber, @RequestParam String destinationAccountNumber, Authentication authentication) {

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if (clientCurrent != null) {

            Set<Account> accountsClientCurrent = clientCurrent.getAccountsActives();

            Account sourceAccount = accountService.findByNumber(sourceAccountNumber);

            Account destinationAccount = accountService.findByNumber(destinationAccountNumber);

            if (sourceAccountNumber.isEmpty()) {
                return new ResponseEntity<>("The source account number is missing", HttpStatus.FORBIDDEN);
            }
            if (sourceAccount == null) {
                return new ResponseEntity<>("The source account does not exist", HttpStatus.FORBIDDEN);
            }
            if (!accountsClientCurrent.contains(sourceAccount)) {
                return new ResponseEntity<>("The selected account does not belong to the authenticated customer", HttpStatus.FORBIDDEN);
            }
            if (destinationAccountNumber.isEmpty()) {
                return new ResponseEntity<>("The destination account number is missing", HttpStatus.FORBIDDEN);
            }
            if (!destinationAccount.isActive()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (destinationAccount == null) {
                return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
            }
            if (sourceAccountNumber.equals(destinationAccountNumber)) {
                return new ResponseEntity<>("Account numbers are equal", HttpStatus.FORBIDDEN);
            }
            if (sourceAccount.getBalance() < amount) {
                return new ResponseEntity<>("The source account does not have the amount available", HttpStatus.FORBIDDEN);
            }
            if (amount <= 0 || amount.isNaN()) {
                return new ResponseEntity<>("The amount is invalid", HttpStatus.FORBIDDEN);
            }
            if (description.isEmpty()) {
                return new ResponseEntity<>("The description is missing", HttpStatus.FORBIDDEN);
            }


            Transaction transactionOrigin = new Transaction(TransactionType.DEBIT, -amount, description + " " + sourceAccountNumber, LocalDateTime.now(), sourceAccount.getBalance() - amount);
            Transaction transactionDestination = new Transaction(TransactionType.CREDIT, +amount, description + " " + destinationAccountNumber, LocalDateTime.now(), destinationAccount.getBalance() + amount);

            destinationAccount.addTransaction(transactionDestination);
            sourceAccount.addTransaction(transactionOrigin);

            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);

            transactionService.saveTransaction(transactionOrigin);
            transactionService.saveTransaction(transactionDestination);
            accountService.saveAccount(sourceAccount);
            accountService.saveAccount(destinationAccount);


            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("You must be authenticated to do this", HttpStatus.FORBIDDEN);
    }

}
