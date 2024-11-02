package com.ebanx.homeassignmentebanx.business.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ebanx.homeassignmentebanx.business.TransactionProcessor;
import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.exception.AccountNotFoundException;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.model.mapper.AccountMapper;
import com.ebanx.homeassignmentebanx.model.mapper.TransactionMapper;
import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;
import com.ebanx.homeassignmentebanx.utils.ResponseUtils;

import lombok.AllArgsConstructor;

@Component("transfer")
@AllArgsConstructor
public class TransferProcessor implements TransactionProcessor {
	private TransactionService transactionService;
	private AccountService accountService;

	@Override
	public Map<String, Object> process(TransactionRequest transactionRequest) {
		Account originAccount = accountService.findById(transactionRequest.getOrigin())
				.orElseThrow(() -> new AccountNotFoundException());
		Account destinationAccount = accountService.findById(transactionRequest.getDestination())
				.orElse(AccountMapper.mapToNewAccount(transactionRequest));
		
		destinationAccount.deposit(transactionRequest.getAmount());
		originAccount.withdraw(transactionRequest.getAmount());
		accountService.save(originAccount);
		accountService.save(destinationAccount);

		Transaction transactionDestination = TransactionMapper.mapToNewTransaction(transactionRequest);
		transactionService.save(transactionDestination);
		
		return ResponseUtils.buildTransferReponse(originAccount, destinationAccount);
	}

}// end of class
