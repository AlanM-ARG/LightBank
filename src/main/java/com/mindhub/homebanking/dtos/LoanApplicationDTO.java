package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private long id;

    private double amount;

    private Integer payments;

    private String destinationAccountNumber;

    public LoanApplicationDTO(long id, double amount, Integer payments, String destinationAccountNumber) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }
}
