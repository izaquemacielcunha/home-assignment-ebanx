package com.ebanx.homeassignmentebanx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewAccount {
	private String type;
	private String destination;
	private Integer amount;

}// end of class
