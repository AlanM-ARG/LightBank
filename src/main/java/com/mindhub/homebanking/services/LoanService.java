package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.Set;

public interface LoanService {

    Set<LoanDTO> getLoansDTO();

    Loan findById(Long id);

    void saveLoan(Loan loan);
}
