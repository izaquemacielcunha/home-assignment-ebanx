package com.ebanx.homeassignmentebanx.service;

import java.util.Map;

import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public interface ProcessorService {
	Map<String, Object> process(TransactionRequest transactionRequest);

}// end of class
