package com.ebanx.homeassignmentebanx.model.mapper;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.NewAccount;

public class TransactionMapper {

	public static Transaction mapToNewAccount(NewAccount newAccount, Account account) {
		Transaction transaction = Transaction.builder()
				.id(null)
				.type(newAccount.getType())
				.amount(newAccount.getAmount())
				.account(account)
				.build();
		return transaction;
	}

}// end of class
