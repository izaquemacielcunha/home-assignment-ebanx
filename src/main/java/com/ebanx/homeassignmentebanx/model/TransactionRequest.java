package com.ebanx.homeassignmentebanx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
	private String type;
	private String destination;
	private String origin;
	private Integer amount;

}// end of class
