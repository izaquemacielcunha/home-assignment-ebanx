package com.ebanx.homeassignmentebanx.business;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ebanx.homeassignmentebanx.exception.TransactionOperationNotFoundException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TransactionProcessorFactory {
	private final Map<String, TransactionProcessor> processors;

	public TransactionProcessor getProcessor(String operationType) {
		TransactionProcessor processor = processors.get(operationType);
		if (processor == null) {
			throw new TransactionOperationNotFoundException("Operation type not found. Operation: " + operationType);
		}
		return processor;
	}

}// end of class
