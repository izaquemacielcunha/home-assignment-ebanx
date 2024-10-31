package com.ebanx.homeassignmentebanx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ebanx.homeassignmentebanx.service.AccountService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountController {
	private AccountService accountService;

	@GetMapping
	public ResponseEntity<Integer> getBalance(@RequestParam(value = "account_id") String destionation) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.getBalanceByDestination(destionation));
	}

}// end of class
