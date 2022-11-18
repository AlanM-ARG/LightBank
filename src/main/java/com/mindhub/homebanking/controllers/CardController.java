package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.CardUtils.getRandomNumber3;
import static com.mindhub.homebanking.utils.CardUtils.getRandomNumberCard;
import static java.util.stream.Collectors.toList;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;


    @GetMapping("/api/cards")
    public List<CardDTO> getCardsDTO() {
        return cardService.getCardsDTO();
    }

    @GetMapping("/api/cards/{id}")
    public CardDTO getCardDTO(@PathVariable Long id) {
        return cardService.getCardDTO(id);
    }

    @GetMapping("/api/clients/current/cards")
    public List<CardDTO> cardsCurrents(Authentication authentication) {
        return clientService.findByEmail(authentication.getName()).getCardsActives().stream().map(card -> new CardDTO(card)).collect(toList());
    }

    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<?> createCard(
            Authentication authentication, @RequestParam CardColor cardColor, @RequestParam CardType cardType) {

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if (clientCurrent != null) {

            Set<Card> cardsActives = clientCurrent.getCardsActives().stream().filter(card -> card.getType() == cardType).collect(Collectors.toSet());

            if (cardsActives.size() >= 3) {
                return new ResponseEntity<>("You can have no more than 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            }
            String randomNumberCard = getRandomNumberCard();
            String cvv = String.format("%03d", getRandomNumber3());

            Card cardCreated = new Card(cardType, cardColor, randomNumberCard, cvv, LocalDate.now(), LocalDate.now().plusYears(5), clientCurrent);

            cardService.saveCard(cardCreated);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("You must be authenticated to do this", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/api/clients/current/cards/{id}")
    public ResponseEntity<?> disableCard(Authentication authentication, @PathVariable Long id){

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if (clientCurrent != null) {

            Set<Card> cardsActiveClient = clientCurrent.getCardsActives();

            Card cardSelected = cardService.getCard(id);

            if(id == null || id <= 0){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (cardSelected == null){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (!cardsActiveClient.contains(cardSelected)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            cardSelected.setActive(false);
            cardService.saveCard(cardSelected);

            return new ResponseEntity<>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

}
