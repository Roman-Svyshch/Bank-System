package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private  AccountsRepository accountsRepository;
    private  CustomerRepository customerRepository;



    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given number " + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccounts(savedCustomer));

    }

    private Accounts createNewAccounts(Customer customer){
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccounts;

    }


    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobile Number", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts","customer id",customer.getId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccounts(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","Account Number",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccount(accountsDto,accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
         isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
               ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
       );
       accountsRepository.deleteByCustomerId(customer.getId());
       customerRepository.deleteById(customer.getId());
     return true;
    }
}
