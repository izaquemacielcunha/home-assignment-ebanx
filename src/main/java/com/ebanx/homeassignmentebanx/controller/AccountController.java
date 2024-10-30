package com.ebanx.homeassignmentebanx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebanx.homeassignmentebanx.service.AccountService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountController {
	private AccountService accountService;

	@GetMapping(path = "/{account_id}")
	public ResponseEntity<Integer> getBook(@PathVariable String destionation) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.getBalanceByDestination(destionation));
	}

}// end of class
