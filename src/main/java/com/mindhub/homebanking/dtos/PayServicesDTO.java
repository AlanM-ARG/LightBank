package com.mindhub.homebanking.dtos;

public class PayServicesDTO {

    private String cardNumber;

    private String cardCvv;

    private Double amount;

    private String description;

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
