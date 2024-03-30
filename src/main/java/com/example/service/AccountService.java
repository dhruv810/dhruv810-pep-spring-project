package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) throws CustomException {
        Account acc = this.accountRepository.findByUserId(account.getUsername());
        if (acc != null) {
            throw new CustomException("Duplicate username");
        }
        return this.accountRepository.save(account);
    }

    public Account login(Account account) throws CustomException {
        Account account2 = this.accountRepository.findByUserId(account.getUsername());
        if (account2 == null) {
            throw new CustomException("Invalid username");
        }
        if (account2.getPassword().equals(account.getPassword())) {
            return account2;
        }
        throw new CustomException("Invalid password");
    }

}
