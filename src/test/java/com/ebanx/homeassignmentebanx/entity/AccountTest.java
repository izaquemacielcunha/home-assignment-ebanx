package com.ebanx.homeassignmentebanx.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ebanx.homeassignmentebanx.exception.InsufficientBalanceException;
import com.ebanx.homeassignmentebanx.exception.InvalidTransactionAmountException;

@SpringBootTest
public class AccountTest {
	private Account account;

	@BeforeEach
	public void setUp() {
		account = Account.builder()
				.id("100")
				.balance(1000)
				.build();
	}

	@Test
	public void shouldtWithdrawValidAmount() {
		account.withdraw(500);
		assertEquals(500, account.getBalance());
	}

	@Test
	public void shouldThrowExceptionForInsufficientBalance() {
		Exception thrownException = assertThrows(InsufficientBalanceException.class, () -> {
			account.withdraw(1500);
		});
		assertEquals("Insufficient balance to perform the withdrawal", thrownException.getMessage());
	}

	@Test
	public void shouldThrowExceptionForNonPositiveAmount() {
		Exception thrownException = assertThrows(InvalidTransactionAmountException.class, () -> {
			account.withdraw(-100);
		});
		assertEquals("The withdrawal amount must be positive", thrownException.getMessage());
	}

	@Test
	public void shouldDepositValidAmount() {
		account.deposit(500);
		assertEquals(1500, account.getBalance());
	}

	@Test
	public void shouldThrowExceptionForNonPositiveAmountDeposit() {
		Exception thrownException = assertThrows(InvalidTransactionAmountException.class, () -> {
			account.deposit(-100);
		});
		assertEquals("The deposit amount must be positive", thrownException.getMessage());
	}

}// end of class
