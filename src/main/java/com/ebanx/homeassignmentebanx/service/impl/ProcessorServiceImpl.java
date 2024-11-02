package com.ebanx.homeassignmentebanx.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebanx.homeassignmentebanx.business.TransactionProcessorFactory;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.service.ProcessorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {
	private TransactionProcessorFactory transactionProcessorFactory;

	@Override
	public Map<String, Object> process(TransactionRequest transactionRequest) {
		return transactionProcessorFactory.getProcessor(transactionRequest.getType()).process(transactionRequest);
	}

}// end of class
