package com.ebanx.homeassignmentebanx.model.mapper;

import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public class TransactionMapper {

	public static Transaction mapToNewDeposit(TransactionRequest transactionRequest) {
		Transaction transaction = Transaction.builder()
				.type(transactionRequest.getType())
				.amount(transactionRequest.getAmount())
				.destination(transactionRequest.getDestination())
				.origin(transactionRequest.getOrigin())
				.build();
		return transaction;
	}
	
}// end of class
