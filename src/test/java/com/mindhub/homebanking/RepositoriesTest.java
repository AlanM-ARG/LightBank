// package com.mindhub.homebanking;

// import com.mindhub.homebanking.models.*;
// import com.mindhub.homebanking.repositories.*;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import java.util.List;

// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.*;
// import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

// @DataJpaTest
// @AutoConfigureTestDatabase(replace = NONE)
// public class RepositoriesTest {


//     @Autowired
//     LoanRepository loanRepository;

//     @Autowired
//     AccountRepository accountRepository;

//     @Autowired
//     ClientRepository clientRepository;

//     @Autowired
//     TransactionRepository transactionRepository;

//     @Autowired
//     CardRepository cardRepository;

//     @MockBean
//     PasswordEncoder passwordEncoder;

//     @Test
//     public void existLoans() {

//         List<Loan> loans = loanRepository.findAll();

//         assertThat(loans, is(not(empty())));

//     }

//     @Test
//     public void existPersonalLoan() {

//         List<Loan> loans = loanRepository.findAll();

//         assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

//     }

//     @Test
//     public void existAccounts() {

//         List<Account> accounts = accountRepository.findAll();

//         assertThat(accounts, is(not(empty())));

//     }

//     @Test
//     public void existAccount1() {

//         List<Account> accounts = accountRepository.findAll();

//         assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))));

//     }

//     @Test
//     public void existClients() {

//         List<Client> clients = clientRepository.findAll();

//         assertThat(clients, is(not(empty())));

//     }

//     @Test
//     public void existClient1() {

//         List<Client> clients = clientRepository.findAll();

//         assertThat(clients, hasItem(hasProperty("id", is(1L))));

//     }

//     @Test
//     public void existTransactions() {

//         List<Transaction> transactions = transactionRepository.findAll();

//         assertThat(transactions, is(not(empty())));

//     }

//     @Test
//     public void existTransactionsPersonalLoanApproved() {

//         List<Transaction> transactions = transactionRepository.findAll();

//         assertThat(transactions, hasItem(hasProperty("description", is("Personal loan approved"))));

//     }

//     @Test
//     public void existCards() {

//         List<Card> cards = cardRepository.findAll();

//         assertThat(cards, is(not(empty())));

//     }

//     @Test
//     public void existCardsDebits() {

//         List<Card> cards = cardRepository.findAll();

//         assertThat(cards, hasItem(hasProperty("type", is(CardType.DEBIT))));

//     }


// }
