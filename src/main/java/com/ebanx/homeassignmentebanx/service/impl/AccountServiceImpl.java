package com.ebanx.homeassignmentebanx.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.exception.AccountNotFoundException;
import com.ebanx.homeassignmentebanx.exception.TransactionOperationNotFoundException;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.model.mapper.AccountMapper;
import com.ebanx.homeassignmentebanx.model.mapper.TransactionMapper;
import com.ebanx.homeassignmentebanx.repository.AccountRepository;
import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;
import com.ebanx.homeassignmentebanx.utils.ResponseUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository accountRepository;
	private TransactionService transactionService;

	@Override
	public Integer getBalanceById(String id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException());
		return account.getBalance();
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}

	@Override
	public Map<String, Object> process(TransactionRequest transactionRequest) {
		Map<String, Object> response = null;
		String operationType = transactionRequest.getType();
		switch (operationType) {
		case "deposit":
			response = hanldleDeposit(transactionRequest);
			break;
		case "withdraw":
			response = hanldleWithdraw(transactionRequest);
			break;
		case "transfer":
			response = hanldleTransfer(transactionRequest);
			break;
		default:
			throw new TransactionOperationNotFoundException("Operation type not found. Operation: " + operationType);
		}
		return response;
	}

	private Map<String, Object> hanldleDeposit(TransactionRequest transactionRequest) {
		Account accountDeposit = findAccountByDestination(transactionRequest);
		if (accountDeposit == null) {
			accountDeposit = AccountMapper.mapToNewAccount(transactionRequest);
		} else {
			accountDeposit.deposit(transactionRequest.getAmount());
		}
		accountRepository.save(accountDeposit);
		Transaction deposit = TransactionMapper.mapToNewDeposit(transactionRequest);
		transactionService.save(deposit);
		return ResponseUtils.buildDepositReponse(accountDeposit);
	}

	private Map<String, Object> hanldleWithdraw(TransactionRequest transactionRequest) {
		Account accountWithdraw = findAccountByOrigin(transactionRequest);
		accountWithdraw.withdraw(transactionRequest.getAmount());
		accountRepository.save(accountWithdraw);
		Transaction withdraw = TransactionMapper.mapToNewDeposit(transactionRequest);
		transactionService.save(withdraw);
		return ResponseUtils.buildWithdrawReponse(accountWithdraw);
	}

	private Map<String, Object> hanldleTransfer(TransactionRequest transactionRequest) {
		Account accountOrigin = findAccountByOrigin(transactionRequest);
		Account accountTransfer = findAccountByDestination(transactionRequest);
		if (accountTransfer == null) {
			accountTransfer = AccountMapper.mapToNewAccount(transactionRequest);
		} else {
			accountTransfer.deposit(transactionRequest.getAmount());
		}
		accountOrigin.withdraw(transactionRequest.getAmount());
		accountRepository.save(accountOrigin);
		accountRepository.save(accountTransfer);
		Transaction transactionDestination = TransactionMapper.mapToNewDeposit(transactionRequest);
		transactionService.save(transactionDestination);
		return ResponseUtils.buildTransferReponse(accountOrigin, accountTransfer);
	}

	private Account findAccountByDestination(TransactionRequest transactionRequest) {
		Account account = accountRepository.findById(transactionRequest.getDestination()).orElse(null);
		return account;
	}

	private Account findAccountByOrigin(TransactionRequest transactionRequest) {
		Account account = accountRepository.findById(transactionRequest.getOrigin()).orElseThrow(() -> new AccountNotFoundException());
		return account;
	}

}// end of class
