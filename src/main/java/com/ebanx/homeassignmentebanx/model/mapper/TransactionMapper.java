package com.ebanx.homeassignmentebanx.model.mapper;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public class TransactionMapper {

	public static Transaction mapToNewTransaction(TransactionRequest transactionRequest, Account account) {
		Transaction transaction = Transaction.builder()
				.id(null)
				.type(transactionRequest.getType())
				.amount(transactionRequest.getAmount())
				.account(account)
				.build();
		return transaction;
	}

}// end of class
