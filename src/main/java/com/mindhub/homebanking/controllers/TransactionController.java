package com.mindhub.homebanking.controllers;


import com.itextpdf.text.DocumentException;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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
            if (!destinationAccount.isActive()) {
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

    @GetMapping("/api/pdf/generate")
    public void generateTransactionsPDF(Authentication authentication, HttpServletResponse response, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String idAccount) throws IOException, DocumentException {
        AccountDTO accountDTO = accountService.getAccountDTO(Long.valueOf(idAccount));
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Pdf pdf = new Pdf();
        pdf.createDocument(response);
        pdf.addTitle("List of Transactions");
        pdf.addLineJumps();
        String formattedStartDate = startDate + ":00.000000";
        String formattedEndDate = endDate + ":00.000000";
        LocalDateTime startDateParsed = LocalDateTime.parse(formattedStartDate);
        LocalDateTime endDateParsed = LocalDateTime.parse(formattedEndDate);
        Set<TransactionDTO> transactions = accountDTO.getTransactions().stream().filter(transaction -> (transaction.getDate().isAfter(startDateParsed) || transaction.getDate().isEqual(startDateParsed)) && (transaction.getDate().isBefore(endDateParsed) || transaction.getDate().isEqual(endDateParsed))).collect(Collectors.toSet());
        pdf.addParagraph("From " + startDate + " to " + endDate);
        pdf.addLineJumps();
        pdf.addAccountTable(accountDTO);
        pdf.addLineJumps();
        pdf.addTransactionsTable(transactions);
        pdf.closeDocument();
}

    @GetMapping("/api/pdf/generate/all")
    public void generateAllTransactionsPDF(Authentication authentication, HttpServletResponse response,@RequestParam String idAccount) throws IOException, DocumentException {
        AccountDTO accountDTO = accountService.getAccountDTO(Long.valueOf(idAccount));
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Pdf pdf = new Pdf();
        pdf.createDocument(response);
        pdf.addTitle("List of Transactions");
        pdf.addLineJumps();
        Set<TransactionDTO> transactions = accountDTO.getTransactions();
        pdf.addAccountTable(accountDTO);
        pdf.addLineJumps();
        pdf.addTransactionsTable(transactions);
        pdf.closeDocument();
    }

}
