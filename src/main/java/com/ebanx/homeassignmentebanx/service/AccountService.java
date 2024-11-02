package com.ebanx.homeassignmentebanx.service;

import java.util.Optional;

import com.ebanx.homeassignmentebanx.entity.Account;

public interface AccountService {
	Integer getBalanceById(String id);
	void deleteAll();
	Optional<Account> findById(String id);
	void save(Account account);

}// end of interface
