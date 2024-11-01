package com.ebanx.homeassignmentebanx.service.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ebanx.homeassignmentebanx.entity.Account;

public class ResponseUtils {

	public static Map<String, Object> buildDepositReponse(Account account) {
		Map<String, Object> response = new LinkedHashMap<>();
		Map<String, Object> accountMap = buildAccountMap(account);
		response.put("destination", accountMap);
		return response;
	}

	public static Map<String, Object> buildWithdrawReponse(Account account) {
		Map<String, Object> response = new LinkedHashMap<>();
		Map<String, Object> accountMap = buildAccountMap(account);
		response.put("origin", accountMap);
		return response;
	}

	public static Map<String, Object> buildTransferReponse(Account accountOrigin, Account accountDestination) {
		Map<String, Object> response = new LinkedHashMap<>();
		Map<String, Object> accountMapOrigin = buildAccountMap(accountOrigin);
		response.put("origin", accountMapOrigin);
		Map<String, Object> accountMapDestination = buildAccountMap(accountDestination);
		response.put("destination", accountMapDestination);
		return response;
	}

	private static Map<String, Object> buildAccountMap(Account account) {
		Map<String, Object> accountMap = new LinkedHashMap<>();
		accountMap.put("id", account.getId());
		accountMap.put("balance", account.getBalance());
		return accountMap;
	}

}// end of class
