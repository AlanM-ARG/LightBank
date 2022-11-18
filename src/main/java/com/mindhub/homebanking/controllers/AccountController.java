package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccountsDTO() {
        return accountService.getAccountsDTO();
    }

    @GetMapping("/api/accounts/{id}")
    public AccountDTO getAccountDTO(
            @PathVariable Long id) {
        return accountService.getAccountDTO(id);
    }

    @GetMapping("/api/clients/current/accounts")
    public List<AccountDTO> accountsCurrents(Authentication authentication) {
        return clientService.findByEmail(authentication.getName()).getAccountsActives().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication) {

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if (clientCurrent != null) {

            if (clientCurrent.getAccountsActives().size() >= 3) {
                return new ResponseEntity<>("You can not have more than 3 accounts", HttpStatus.FORBIDDEN);
            }

            Set<String> accountsNumber = accountService.getAccountsNumbers();
            long randomNumber;
            do {
                randomNumber = (long) (Math.random() * (100000000 - 1) + 1);
            } while (accountsNumber.contains("VIN" + randomNumber));
            String accountNumber = "VIN" + randomNumber;
            Account accountCreated = new Account(accountNumber, LocalDateTime.now(), 0);

            clientCurrent.addAccount(accountCreated);

            accountService.saveAccount(accountCreated);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("You must be authenticated to do this", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/api/clients/current/accounts/{id}")
    public ResponseEntity<?> disableAccount (Authentication authentication, @PathVariable Long id ){

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if (clientCurrent != null) {

            Set<Account> clientAccountsActives = clientCurrent.getAccountsActives();
            Account accountSelected = accountService.getAccount(id);
            Set<Transaction> transactionsSelected = accountSelected.getTransactionsActives();
            if (id == null || id<=0){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (clientAccountsActives.isEmpty()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (!clientAccountsActives.contains(accountSelected)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (!transactionsSelected.isEmpty()){
                transactionsSelected.forEach(transaction -> {
                    transaction.setActive(false);
                    transactionService.saveTransaction(transaction);
                });
            }

            accountSelected.setActive(false);
            accountService.saveAccount(accountSelected);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
