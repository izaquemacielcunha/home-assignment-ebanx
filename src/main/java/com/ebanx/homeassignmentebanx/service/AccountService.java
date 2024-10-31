package com.ebanx.homeassignmentebanx.service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public interface AccountService {
	Integer getBalanceByDestination(String destination);
	Account process(TransactionRequest createAccount);
	void deleteAll();

}// end of interface
