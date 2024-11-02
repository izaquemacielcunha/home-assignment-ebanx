package com.ebanx.homeassignmentebanx;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ebanx.homeassignmentebanx.business.TransactionProcessorFactoryTest;
import com.ebanx.homeassignmentebanx.business.impl.DepositProcessorTest;
import com.ebanx.homeassignmentebanx.business.impl.TransferProcessorTest;
import com.ebanx.homeassignmentebanx.business.impl.WithdrawProcessorTest;
import com.ebanx.homeassignmentebanx.entity.AccountTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Suite
@SelectClasses({ 
	TransactionProcessorFactoryTest.class,
	DepositProcessorTest.class,
	TransferProcessorTest.class,
	WithdrawProcessorTest.class,
	AccountTest.class

})
class HomeAssignmentEbanxApplicationTests {

	@Test
	void contextLoads() {
	}

}
