package com.ebanx.homeassignmentebanx.entity;

import com.ebanx.homeassignmentebanx.exception.InsufficientBalanceException;
import com.ebanx.homeassignmentebanx.exception.InvalidTransactionAmountException;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	private String id;
	private Integer balance;

	public void withdraw(int amount) {
		if (amount > balance) {
			throw new InsufficientBalanceException("Insufficient balance to perform the withdrawal");
		}
		if (amount <= 0) {
			throw new InvalidTransactionAmountException("The withdrawal amount must be positive");
		}
		this.balance -= amount;
	}

	public void deposit(int amount) {
		if (amount <= 0) {
			throw new InvalidTransactionAmountException("The deposit amount must be positive");
		}
		this.balance += amount;
	}

}// end of class
