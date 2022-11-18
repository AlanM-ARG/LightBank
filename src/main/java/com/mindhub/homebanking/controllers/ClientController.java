package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/api/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTO(id);
    }

    @GetMapping("/api/clients/current")
    public ClientDTO getClientAuthenticated(Authentication authentication) {
        return clientService.getClientAuthenticatedDTO(authentication);
    }

    @PatchMapping("/api/clients/current")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestParam String password) {
        Client clientCurrent = clientService.findByEmail(authentication.getName());
        clientCurrent.setPassword(passwordEncoder.encode(password));
        clientService.saveClient(clientCurrent);
        return new ResponseEntity<>(clientCurrent, HttpStatus.OK);
    }

    @PostMapping("/api/clients")
    public ResponseEntity<?> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("The first name is missing", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {
            return new ResponseEntity<>("The last name is missing", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("The email is missing", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("The password is missing", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }


        Set<String> accountsNumber = accountService.getAccountsNumbers();
        long randomNumber;
        do {
            randomNumber = (long) (Math.random() * (100000000 - 1) + 1);
        } while (accountsNumber.contains("VIN" + randomNumber));
        String accountNumber = "VIN" + randomNumber;
        Account accountCreated = new Account(accountNumber, LocalDateTime.now(), 0);
        Client clientCreated = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        clientCreated.addAccount(accountCreated);

        clientService.saveClient(clientCreated);
        accountService.saveAccount(accountCreated);

        return new ResponseEntity<>(HttpStatus.CREATED);


    }
}
