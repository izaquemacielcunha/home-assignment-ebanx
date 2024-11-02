package com.ebanx.homeassignmentebanx.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.entity.Transaction;
import com.ebanx.homeassignmentebanx.exception.AccountNotFoundException;
import com.ebanx.homeassignmentebanx.exception.InsufficientBalanceException;
import com.ebanx.homeassignmentebanx.exception.InvalidTransactionAmountException;
import com.ebanx.homeassignmentebanx.model.TransactionRequest;
import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WithdrawProcessorTest {
	@Mock
	private TransactionService transactionService;

	@Mock
	private AccountService accountService;

	@InjectMocks
	private WithdrawProcessor withdrawProcessor;

	private TransactionRequest transactionRequestNonExistingAccount;
	private TransactionRequest transactionRequestExistingAccount;
	private TransactionRequest transactionRequestInsufficientBalance;
	private TransactionRequest transactionRequestNonPositiveAmount;

	@BeforeEach
	void setUp() {
		transactionRequestNonExistingAccount = TransactionRequest.builder()
				.type("withdraw")
				.origin("100")
				.amount(10)
				.build();
		transactionRequestExistingAccount = TransactionRequest.builder()
				.type("withdraw")
				.origin("200")
				.amount(10)
				.build();
		transactionRequestInsufficientBalance = TransactionRequest.builder()
				.type("withdraw")
				.origin("300")
				.amount(20)
				.build();
		transactionRequestNonPositiveAmount = TransactionRequest.builder()
				.type("withdraw")
				.origin("400")
				.amount(-5)
				.build();
	}

	@Test
	void shouldThrowExceptionForNonExistentAccount() {
		when(accountService.findById("100")).thenReturn(Optional.empty());

		AccountNotFoundException thrownException = assertThrows(AccountNotFoundException.class,
				() -> withdrawProcessor.process(transactionRequestNonExistingAccount));

		assertEquals(null, thrownException.getMessage());
	}

	@Test
	void shouldProcessWithdrawForExistingAccount() {
		Account existingAccount = Account.builder().id("200").balance(15).build();
		when(accountService.findById("200")).thenReturn(Optional.of(existingAccount));

		Map<String, Object> response = withdrawProcessor.process(transactionRequestExistingAccount);

		@SuppressWarnings("unchecked")
		Map<String, Object> origin = (Map<String, Object>) response.get("origin");

		assertEquals("200", origin.get("id"));
		assertEquals(5, origin.get("balance"));
		verify(accountService, times(1)).save(existingAccount);
		verify(transactionService, times(1)).save(any(Transaction.class));
	}
	
	@Test
	void shouldThrowExceptionForInsufficientBalance() {
		Account existingAccount = Account.builder().id("300").balance(5).build();
		when(accountService.findById("300")).thenReturn(Optional.of(existingAccount));

		InsufficientBalanceException thrownException = assertThrows(InsufficientBalanceException.class,
				() -> withdrawProcessor.process(transactionRequestInsufficientBalance));

		assertEquals("Insufficient balance to perform the withdrawal", thrownException.getMessage());
	}
	
	@Test
	void shouldThrowExceptionForNonPositiveAmount() {
		Account existingAccount = Account.builder().id("400").balance(5).build();
		when(accountService.findById("400")).thenReturn(Optional.of(existingAccount));

		InvalidTransactionAmountException thrownException = assertThrows(InvalidTransactionAmountException.class,
				() -> withdrawProcessor.process(transactionRequestNonPositiveAmount));

		assertEquals("The withdrawal amount must be positive", thrownException.getMessage());
	}


}// end of class
