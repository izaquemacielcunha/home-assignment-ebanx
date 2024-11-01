package com.ebanx.homeassignmentebanx.entity;

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
	
	//TODO create dto
	
	public void withdraw(int amount) {
		this.balance -= amount;
		//TODO throw exception when amount is bigger than balance
	}
	
	public void deposit(int amount) {
		this.balance += amount;
		//TODO throw exception when amount is negative
	}

}// end of class
