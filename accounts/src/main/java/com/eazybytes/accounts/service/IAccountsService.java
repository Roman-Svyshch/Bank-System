package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.repository.AccountsRepository;

public interface IAccountsService  {
    /**
     *
     * @param customerDto - CustomerDto Object
     * */
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccounts(CustomerDto customerDto);
    boolean deleteAccount(String mobileNumber);
}
