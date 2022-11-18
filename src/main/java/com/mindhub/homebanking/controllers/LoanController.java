package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/api/loans")
    public Set<LoanDTO> getLoansDTO() {
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<?> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if (clientCurrent != null) {

            Set<Account> clientAccounts = clientCurrent.getAccountsActives();

            Loan loanRequested = loanService.findById(loanApplicationDTO.getId());

            Set<Loan> loansClientCurrent = clientCurrent.getClientLoans().stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toSet());

            Account destinationAccount = accountService.findByNumber(loanApplicationDTO.getDestinationAccountNumber());

            if (loanRequested == null) {
                return new ResponseEntity<>("The loan does not exist", HttpStatus.FORBIDDEN);
            }
            if (loansClientCurrent.contains(loanRequested)) {
                return new ResponseEntity<>("You already have a loan of this type", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getPayments() <= 0) {
                return new ResponseEntity<>("The loan payments is invalid", HttpStatus.FORBIDDEN);
            }
            if (!loanRequested.getPayments().contains(loanApplicationDTO.getPayments())) {
                return new ResponseEntity<>("Selected payments not available on this loan", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getAmount() <= 0) {
                return new ResponseEntity<>("The amount is invalid", HttpStatus.FORBIDDEN);
            }
            if (loanApplicationDTO.getAmount() > loanRequested.getMaxAmount()) {
                return new ResponseEntity<>("The requested amount exceeds the maximum loan amount", HttpStatus.FORBIDDEN);
            }
            if (destinationAccount == null) {
                return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
            }
            if (!destinationAccount.isActive()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (!clientAccounts.contains(destinationAccount)) {
                return new ResponseEntity<>("The destination account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
            }

            double amountPlusInterest = 0;

            if(loanRequested.getName().equals("Mortgage")){
                amountPlusInterest = loanApplicationDTO.getAmount() * loanRequested.getPercentage();
            }
            if(loanRequested.getName().equals("Personal")){
                amountPlusInterest = loanApplicationDTO.getAmount() * loanRequested.getPercentage();
            }
            if(loanRequested.getName().equals("Automobile")){
                amountPlusInterest = loanApplicationDTO.getAmount() * loanRequested.getPercentage();
            }

            ClientLoan newClientLoan = new ClientLoan(clientCurrent, loanRequested, amountPlusInterest, loanApplicationDTO.getPayments());
            Transaction accreditation = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loanRequested.getName() + " loan approved", LocalDateTime.now());

            destinationAccount.addTransaction(accreditation);
            destinationAccount.setBalance(destinationAccount.getBalance() + accreditation.getAmount());

            clientLoanRepository.save(newClientLoan);
            accountService.saveAccount(destinationAccount);
            transactionService.saveTransaction(accreditation);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("You must be authenticated to do this", HttpStatus.FORBIDDEN);

    }
}
