package com.ebanx.homeassignmentebanx.service.impl;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.NewAccount;
import com.ebanx.homeassignmentebanx.model.mapper.AccountMapper;
import com.ebanx.homeassignmentebanx.model.mapper.TransactionMapper;
import com.ebanx.homeassignmentebanx.repository.AccountRepository;
import com.ebanx.homeassignmentebanx.repository.TransactionRepository;
import com.ebanx.homeassignmentebanx.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;

	@Override
	public Integer getBalanceByDestination(String destination) {
		return accountRepository.getBalanceByDestination(destination);
		// TODO throw notfound exception
	}

	public Account save(NewAccount createAccount) {
		Account account = accountRepository.save(AccountMapper.mapToNewAccount(createAccount));
		Transaction transaction = TransactionMapper.mapToNewAccount(createAccount, account);
		transactionRepository.save(transaction);
		// TODO throw already exists exception and map to save all in the same transaction
		return account;
	}

}// end of class
