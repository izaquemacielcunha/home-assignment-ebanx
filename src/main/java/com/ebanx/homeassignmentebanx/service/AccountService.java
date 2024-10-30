package com.ebanx.homeassignmentebanx.service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.model.NewAccount;

public interface AccountService {
	Integer getBalanceByDestination(String destination);
	Account save(NewAccount createAccount);

}// end of interface
