package com.ebanx.homeassignmentebanx.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private String destination;
	private Integer balance;
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Transaction> transactions;
	
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
