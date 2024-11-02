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
public class TransferProcessorTest {
	@Mock
	private TransactionService transactionService;

	@Mock
	private AccountService accountService;

	@InjectMocks
	private TransferProcessor transferProcessor;

	private TransactionRequest transactionRequestNonExistingAccount;
	private TransactionRequest transactionRequestExistingAccount;
	private TransactionRequest transactionRequestInsufficientBalance;
	private TransactionRequest transactionRequestNonPositiveAmount;
	
	@BeforeEach
	void setUp() {
		transactionRequestNonExistingAccount = TransactionRequest.builder()
				.type("transfer")
				.origin("100")
				.destination("200")
				.amount(10)
				.build();
		transactionRequestExistingAccount = TransactionRequest.builder()
				.type("transfer")
				.origin("300")
				.destination("400")
				.amount(10)
				.build();
		transactionRequestInsufficientBalance = TransactionRequest.builder()
				.type("transfer")
				.origin("500")
				.destination("600")
				.amount(20)
				.build();
		transactionRequestNonPositiveAmount = TransactionRequest.builder()
				.type("withdraw")
				.origin("700")
				.destination("800")
				.amount(-5)
				.build();
	}
	
	@Test
	void shouldThrowExceptionForNonExistentOriginAccount() {
		when(accountService.findById("100")).thenReturn(Optional.empty());

		AccountNotFoundException thrownException = assertThrows(AccountNotFoundException.class,
				() -> transferProcessor.process(transactionRequestNonExistingAccount));

		assertEquals(null, thrownException.getMessage());
	}
	
	@Test
	void shouldProcessTransferForExistingOriginAccountAndNonExistentDestinationAccount() {
		Account existingAccount = Account.builder().id("300").balance(15).build();
		when(accountService.findById("300")).thenReturn(Optional.of(existingAccount));

		Map<String, Object> response = transferProcessor.process(transactionRequestExistingAccount);

		@SuppressWarnings("unchecked")
		Map<String, Object> origin = (Map<String, Object>) response.get("origin");
		@SuppressWarnings("unchecked")
		Map<String, Object> destination = (Map<String, Object>) response.get("destination");

		assertEquals("300", origin.get("id"));
		assertEquals(5, origin.get("balance"));
		assertEquals("400", destination.get("id"));
		assertEquals(10, destination.get("balance"));
		verify(accountService, times(2)).save(any(Account.class));
		verify(transactionService, times(1)).save(any(Transaction.class));
	}
	
	@Test
	void shouldProcessTransferForExistingOriginAndDestinationAccount() {
		Account existingOriginAccount = Account.builder().id("300").balance(20).build();
		when(accountService.findById("300")).thenReturn(Optional.of(existingOriginAccount));
		
		Account existingDestinationAccount = Account.builder().id("400").balance(5).build();
		when(accountService.findById("400")).thenReturn(Optional.of(existingDestinationAccount));

		Map<String, Object> response = transferProcessor.process(transactionRequestExistingAccount);

		@SuppressWarnings("unchecked")
		Map<String, Object> origin = (Map<String, Object>) response.get("origin");
		@SuppressWarnings("unchecked")
		Map<String, Object> destination = (Map<String, Object>) response.get("destination");

		assertEquals("300", origin.get("id"));
		assertEquals(10, origin.get("balance"));
		assertEquals("400", destination.get("id"));
		assertEquals(15, destination.get("balance"));
		verify(accountService, times(2)).save(any(Account.class));
		verify(transactionService, times(1)).save(any(Transaction.class));
	}
	
	@Test
	void shouldThrowExceptionForInsufficientBalance() {
		Account existingAccount = Account.builder().id("500").balance(10).build();
		when(accountService.findById("500")).thenReturn(Optional.of(existingAccount));

		InsufficientBalanceException thrownException = assertThrows(InsufficientBalanceException.class,
				() -> transferProcessor.process(transactionRequestInsufficientBalance));

		assertEquals("Insufficient balance to perform the withdrawal", thrownException.getMessage());
	}
	
	@Test
	void shouldThrowExceptionForNonPositiveAmount() {
		Account existingAccount = Account.builder().id("700").balance(5).build();
		when(accountService.findById("700")).thenReturn(Optional.of(existingAccount));

		InvalidTransactionAmountException thrownException = assertThrows(InvalidTransactionAmountException.class,
				() -> transferProcessor.process(transactionRequestNonPositiveAmount));

		assertEquals("The withdrawal amount must be positive", thrownException.getMessage());
	}
	
	

}// end of class
