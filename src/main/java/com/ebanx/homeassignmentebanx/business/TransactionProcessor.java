package com.ebanx.homeassignmentebanx.business;

import java.util.Map;

import com.ebanx.homeassignmentebanx.model.TransactionRequest;

public interface TransactionProcessor {
	Map<String, Object> process(TransactionRequest transactionRequest);

}// end of interface
