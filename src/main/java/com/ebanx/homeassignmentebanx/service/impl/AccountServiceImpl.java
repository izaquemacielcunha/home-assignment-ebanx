package com.ebanx.homeassignmentebanx.service.impl;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.model.mapper.AccountMapper;
import com.ebanx.homeassignmentebanx.model.mapper.TransactionMapper;
import com.ebanx.homeassignmentebanx.repository.AccountRepository;
import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository accountRepository;
	private TransactionService transactionService;

	@Override
	public Integer getBalanceByDestination(String destination) {
		Account account = accountRepository.findById(destination).get();
		return account.getBalance();
		// TODO throw notfound exception
	}

	public Account process(TransactionRequest transactionRequest) {
		Account accountDestination = findAccountById(transactionRequest.getDestination());
		switch (transactionRequest.getType()) {
		case "deposit":
			if (accountDestination == null) {
				accountDestination = accountRepository.save(AccountMapper.mapToNewAccount(transactionRequest));
			}
			break;
		case "withdraw":
			if (accountDestination != null) {
				accountDestination.withdraw(transactionRequest.getAmount());
				accountRepository.save(accountDestination); // TODO maybe this can ben external, is called many times
			} else {
				// TODO throw exception
			}
			break;
		case "transfer":
			Account accountOrigin = findAccountById(transactionRequest.getOrigin());
			if (accountDestination != null && accountOrigin != null) {
				accountOrigin.withdraw(transactionRequest.getAmount());
				accountDestination.deposit(transactionRequest.getAmount());
				accountRepository.save(accountOrigin); // TODO maybe this can ben external, is called many times
				accountRepository.save(accountDestination); // TODO maybe this can ben external, is called many times
				TransactionRequest deposit = TransactionRequest.builder()
						.type("deposit")
						.origin(transactionRequest.getOrigin())
						.destination(transactionRequest.getDestination())
						.build(); // TODO refactor this
				Transaction transactionDestination = TransactionMapper.mapToNewTransaction(deposit, accountDestination);// TODO refactor this
				transactionService.save(transactionDestination);// TODO refactor this
			} else {
				// TODO throw exception
			}
			break;
		default:
			// TODO throw exception
			break;

		}
		Transaction transaction = TransactionMapper.mapToNewTransaction(transactionRequest, accountDestination);
		transactionService.save(transaction);
		return accountDestination;
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}

	private Account findAccountById(String destination) {
		Account account = accountRepository.findById(destination).orElse(null);
		return account;
	}

}// end of class
