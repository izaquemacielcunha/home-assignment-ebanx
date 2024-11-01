package com.ebanx.homeassignmentebanx.service;

import java.util.Map;

import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public interface AccountService {
	Integer getBalanceById(String id);
	Map<String, Object> process(TransactionRequest createAccount);
	void deleteAll();

}// end of interface
