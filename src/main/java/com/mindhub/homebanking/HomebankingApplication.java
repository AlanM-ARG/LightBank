package com.mindhub.homebanking;

import com.itextpdf.text.DocumentException;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileNotFoundException;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args)  {
		SpringApplication.run(HomebankingApplication.class, args);


	}


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) throws DocumentException, FileNotFoundException {
		return args -> {

//			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123") );
//
//			Client client2 = new Client("Alan", "Morua", "alanmorua8@gmail.com", passwordEncoder.encode("alan123"));
//
//			Client client3 = new Client("admin", "lastAdmin", "admin@admin.com", passwordEncoder.encode("123"));
//			Account account1 = new Account( "VIN001", LocalDateTime.now(), 5000, AccountType.SAVINGS);
//
//			Account account2 = new Account( "VIN002", LocalDateTime.now().plusDays(1), 7500,AccountType.CHECKING);
//
//			Account account3 = new Account( "VIN003", LocalDateTime.now(), 5000, AccountType.SAVINGS);
//			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -5000, "credit personal", LocalDateTime.now());
//			Transaction transaction2 = new Transaction(TransactionType.CREDIT, +5000, "Netflix", LocalDateTime.now());
//			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -5000, "Coffe", LocalDateTime.now());
//			Transaction transaction4 = new Transaction(TransactionType.CREDIT, +5000, "McDonalds", LocalDateTime.now());
//			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -5000, "Amazing Events", LocalDateTime.now());
//			Transaction transaction6 = new Transaction(TransactionType.CREDIT, +5000, "India Petshop", LocalDateTime.now());
//			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -5000, "Secure Life", LocalDateTime.now());
//			Transaction transaction8 = new Transaction(TransactionType.CREDIT, +5000, "Home alarm", LocalDateTime.now());
//			Transaction transaction9 = new Transaction(TransactionType.DEBIT, -5000, "Expenses", LocalDateTime.now());
//			Transaction transaction10 = new Transaction(TransactionType.CREDIT, +5000, "Home water", LocalDateTime.now());
//
//			clientRepository.save(client1);
//			client1.addAccount(account1);
//			client2.addAccount(account3);
//			clientRepository.save(client2);
//			clientRepository.save(client3);
//			account1.addTransaction(transaction1);
//			account1.addTransaction(transaction2);
//			account1.addTransaction(transaction3);
//			account1.addTransaction(transaction4);
//			account1.addTransaction(transaction5);
//			account1.addTransaction(transaction6);
//			account1.addTransaction(transaction7);
//			account1.addTransaction(transaction8);
//			account1.addTransaction(transaction9);
//			account1.addTransaction(transaction10);
//			client1.addAccount(account2);
//			Card card1 = new Card(CardType.DEBIT, CardColor.GOLD, "1234-5678-8765-4321" , "321", LocalDate.now(), LocalDate.now().plusYears(5), client1);
//			cardRepository.save(card1);
//			Card card2 = new Card(CardType.CREDIT, CardColor.TITANIUM, "1234-5656-1234-4321", "123", LocalDate.now(), LocalDate.now().plusYears(5), client1);
//			cardRepository.save(card2);
//			Card card3 = new Card(CardType.CREDIT, CardColor.SILVER, "1234-5665-4321-8998", "456", LocalDate.now(), LocalDate.now().plusYears(5), client2);
//			cardRepository.save(card3);
//
//			accountRepository.save(account1);
//
//			accountRepository.save(account2);
//
//			accountRepository.save(account3);
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//			transactionRepository.save(transaction5);
//			transactionRepository.save(transaction6);
//			transactionRepository.save(transaction7);
//			transactionRepository.save(transaction8);
//			transactionRepository.save(transaction9);
//			transactionRepository.save(transaction10);
//
//			Loan loan1 = new Loan("Mortgage", 500000, 1.20 , List.of(12, 24, 36, 48, 60));
//			loanRepository.save(loan1);
//			Loan loan2 = new Loan("Personal", 100000, 1.15,List.of(6, 12, 24));
//			loanRepository.save(loan2);
//			Loan loan3 = new Loan("Automobile", 300000, 1.10 ,List.of(6, 12, 24, 36));
//			loanRepository.save(loan3);
//
//			ClientLoan clientLoan1 = new ClientLoan(client1, loan1, 400000, 60);
//			clientLoanRepository.save(clientLoan1);
//			ClientLoan clientLoan2 = new ClientLoan(client1, loan2, 50000, 12);
//			clientLoanRepository.save(clientLoan2);
//			ClientLoan clientLoan3 = new ClientLoan(client2, loan2, 100000, 24);
//			clientLoanRepository.save(clientLoan3);
//			ClientLoan clientLoan4 = new ClientLoan(client2, loan3, 200000, 36);
//			clientLoanRepository.save(clientLoan4);


			System.out.println("Started");

		};
	}
}
