package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private String name;
    private double amount;

    private Integer payment;


    public ClientLoan() {
    }

    public ClientLoan(Client client, Loan loan, double amount, Integer payment) {
        this.client = client;
        this.loan = loan;
        this.amount = amount;
        this.payment = payment;
        this.name = loan.getName();

    }


    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", client=" + client +
                ", loan=" + loan +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", payment=" + payment +
                '}';
    }
}
