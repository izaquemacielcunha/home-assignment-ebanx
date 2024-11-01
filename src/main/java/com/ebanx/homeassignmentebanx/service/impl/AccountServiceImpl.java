package com.ebanx.homeassignmentebanx.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.model.mapper.AccountMapper;
import com.ebanx.homeassignmentebanx.model.mapper.TransactionMapper;
import com.ebanx.homeassignmentebanx.repository.AccountRepository;
import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;
import com.ebanx.homeassignmentebanx.service.utils.ResponseUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private AccountRepository accountRepository;
	private TransactionService transactionService;

	@Override
	public Integer getBalanceById(String id) {
		Account account = accountRepository.findById(id).get();
		return account.getBalance();
		// TODO throw notfound exception
	}

	public Map<String, Object> process(TransactionRequest transactionRequest) {
		Map<String, Object> response = null;
		switch (transactionRequest.getType()) {
		case "deposit":
			Account accountDeposit = findAccountByDestination(transactionRequest);
			if (accountDeposit == null) {
				accountDeposit = AccountMapper.mapToNewAccount(transactionRequest);
			} else {
				accountDeposit.deposit(transactionRequest.getAmount());
			}
			accountRepository.save(accountDeposit);
			Transaction deposit = TransactionMapper.mapToNewDeposit(transactionRequest);// TODO maybe this can ben external, is called many times
			transactionService.save(deposit);// TODO maybe this can ben external, is called many times
			response = ResponseUtils.buildDepositReponse(accountDeposit);
			break;
		case "withdraw":
			Account accountWithdraw = findAccountByOrigin(transactionRequest);
			if (accountWithdraw != null && transactionRequest.getOrigin() != null) { // TODO valid if is empty
				accountWithdraw.withdraw(transactionRequest.getAmount());
				accountRepository.save(accountWithdraw); // TODO maybe this can ben external, is called many times
				Transaction withdraw = TransactionMapper.mapToNewDeposit(transactionRequest);// TODO maybe this can ben external, is called many times
				transactionService.save(withdraw);
				response = ResponseUtils.buildWithdrawReponse(accountWithdraw);
			} else {
				// TODO throw exception
			}
			break;
		case "transfer":
			Account accountOrigin = findAccountByOrigin(transactionRequest);
			if (accountOrigin != null) {
				Account accountTransfer = findAccountByDestination(transactionRequest);
				if (accountTransfer == null) {
					accountTransfer = AccountMapper.mapToNewAccount(transactionRequest);
				} else {
					accountTransfer.deposit(transactionRequest.getAmount());
				}
				accountOrigin.withdraw(transactionRequest.getAmount());
				accountRepository.save(accountOrigin); // TODO maybe this can ben external, is called many times
				accountRepository.save(accountTransfer); // TODO maybe this can ben external, is called many times
				Transaction transactionDestination = TransactionMapper.mapToNewDeposit(transactionRequest);// TODO refactor this
				transactionService.save(transactionDestination);// TODO refactor this
				response = ResponseUtils.buildTransferReponse(accountOrigin, accountTransfer);
			} else {
				// TODO throw exception
			}
			break;
		default:
			// TODO throw exception
			break;

		}
		return response;
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}

	private Account findAccountByDestination(TransactionRequest transactionRequest) {
		Account account = accountRepository.findById(transactionRequest.getDestination()).orElse(null);
		return account;
	}

	private Account findAccountByOrigin(TransactionRequest transactionRequest) {
		Account account = accountRepository.findById(transactionRequest.getOrigin()).orElse(null);
		return account;
	}

}// end of class
