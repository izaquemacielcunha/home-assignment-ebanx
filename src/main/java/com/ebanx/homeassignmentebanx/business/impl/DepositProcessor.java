package com.ebanx.homeassignmentebanx.business.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ebanx.homeassignmentebanx.business.TransactionProcessor;
import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.model.mapper.AccountMapper;
import com.ebanx.homeassignmentebanx.model.mapper.TransactionMapper;
import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;
import com.ebanx.homeassignmentebanx.utils.ResponseUtils;

import lombok.AllArgsConstructor;

@Component("deposit")
@AllArgsConstructor
public class DepositProcessor implements TransactionProcessor {
	private TransactionService transactionService;
	private AccountService accountService;

	@Override
	public Map<String, Object> process(TransactionRequest transactionRequest) {
		Account account = accountService.findById(transactionRequest.getDestination())
				.orElse(AccountMapper.mapToNewAccount(transactionRequest));
		account.deposit(transactionRequest.getAmount());
		accountService.save(account);

		Transaction deposit = TransactionMapper.mapToNewTransaction(transactionRequest);
		transactionService.save(deposit);

		return ResponseUtils.buildDepositReponse(account);
	}

}
// end of class
