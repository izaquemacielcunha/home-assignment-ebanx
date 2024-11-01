package com.ebanx.homeassignmentebanx.service.impl;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.repository.TransactionRepository;
import com.ebanx.homeassignmentebanx.service.TransactionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private TransactionRepository transactionRepository;

	@Override
	public void save(Transaction transaction) {
		transactionRepository.save(transaction);
	}

	@Override
	public void deleteAll() {
		transactionRepository.deleteAll();
	}

}// end of class
