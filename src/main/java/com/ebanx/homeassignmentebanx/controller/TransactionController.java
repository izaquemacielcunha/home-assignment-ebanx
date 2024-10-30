package com.ebanx.homeassignmentebanx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebanx.homeassignmentebanx.entity.Account;
import com.ebanx.homeassignmentebanx.model.NewAccount;
import com.ebanx.homeassignmentebanx.service.AccountService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransactionController {
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<Account> save(@RequestBody NewAccount newAccount) {
		Account account = accountService.save(newAccount);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

}// end of class
