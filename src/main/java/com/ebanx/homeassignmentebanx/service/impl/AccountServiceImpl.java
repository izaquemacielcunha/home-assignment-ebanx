package com.ebanx.homeassignmentebanx.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.exception.AccountNotFoundException;
import com.ebanx.homeassignmentebanx.repository.AccountRepository;
import com.ebanx.homeassignmentebanx.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository accountRepository;

	@Override
	public Integer getBalanceById(String id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException());
		return account.getBalance();
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}

	@Override
	public Optional<Account> findById(String id) {
		Optional<Account> account = accountRepository.findById(id);
		return account;
	}

	@Override
	public void save(Account account) {
		accountRepository.save(account);
	}

}// end of class
