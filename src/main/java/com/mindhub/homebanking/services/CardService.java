package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    List<CardDTO> getCardsDTO();

    CardDTO getCardDTO(Long id);

    Card getCard(Long id);

    void saveCard(Card card);

}
