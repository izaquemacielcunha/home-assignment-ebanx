package com.ebanx.homeassignmentebanx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebanx.homeassignmentebanx.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	Integer getBalanceByDestination(String destination);

}// end of interface