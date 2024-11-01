package com.ebanx.homeassignmentebanx.model.mapper;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public class AccountMapper {

	public static Account mapToNewAccount(TransactionRequest newAccount) {
		Account account = Account.builder()
				.id(newAccount.getDestination())
				.balance(newAccount.getAmount())
				.build();
		return account;
	}

}// end of class
