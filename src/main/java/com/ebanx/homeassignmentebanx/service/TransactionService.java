package com.ebanx.homeassignmentebanx.service;

import com.ebanx.homeassignmentebanx.entity.Transaction;

public interface TransactionService {
	void save(Transaction transaction);
	void deleteAll();

}// end of interface
