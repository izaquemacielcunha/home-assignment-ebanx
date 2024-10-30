package com.ebanx.homeassignmentebanx.model.mapper;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.model.NewAccount;

public class AccountMapper {

	public static Account mapToNewAccount(NewAccount newAccount) {
		Account account = Account.builder()
				.destination(newAccount.getDestination())
				.balance(newAccount.getAmount())
				.transactions(null)
				.build();
		return account;
	}

}// end of class
