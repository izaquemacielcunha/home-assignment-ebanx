package com.ebanx.homeassignmentebanx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebanx.homeassignmentebanx.service.AccountService;
import com.ebanx.homeassignmentebanx.service.TransactionService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ResetController {
	private AccountService accountService;
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<String> reset(){
		accountService.deleteAll();
		transactionService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}

}// end of class
