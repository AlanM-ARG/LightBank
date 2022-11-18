package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {

    Set<String> getAccountsNumbers();

    void saveAccount(Account account);

    List<AccountDTO> getAccountsDTO();

    AccountDTO getAccountDTO(Long id);

    Account getAccount(Long id);

    Account findByNumber(String number);
}
