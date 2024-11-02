package com.ebanx.homeassignmentebanx.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ebanx.homeassignmentebanx.exception.TransactionOperationNotFoundException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionProcessorFactoryTest {

	@Mock
	private TransactionProcessor mockProcessorDeposit;

	@Mock
	private TransactionProcessor mockProcessorWithdraw;

	@Mock
	private TransactionProcessor mockProcessorTransfer;

	@InjectMocks
	private TransactionProcessorFactory transactionProcessorFactory;

	private final Map<String, TransactionProcessor> processors = new HashMap<>();

	@BeforeEach
	void setUp() {
		processors.put("deposit", mockProcessorDeposit);
		processors.put("withdraw", mockProcessorWithdraw);
		processors.put("transfer", mockProcessorTransfer);

		transactionProcessorFactory = new TransactionProcessorFactory(processors);
	}

	@Test
	void shouldReturnProcessorForOperationTypeDeposit() {
		String operationType = "deposit";

		TransactionProcessor processor = transactionProcessorFactory.getProcessor(operationType);

		assertEquals(mockProcessorDeposit, processor);
	}

	@Test
	void shouldReturnProcessorForOperationTypeWithdraw() {
		String operationType = "withdraw";

		TransactionProcessor processor = transactionProcessorFactory.getProcessor(operationType);

		assertEquals(mockProcessorWithdraw, processor);
	}

	@Test
	void shouldReturnProcessorForOperationTypeTransfer() {
		String operationType = "transfer";

		TransactionProcessor processor = transactionProcessorFactory.getProcessor(operationType);

		assertEquals(mockProcessorTransfer, processor);
	}

	@Test
	void shouldThrowExceptionForNonExistentOperationType() {
		String operationType = "unknown_operation";

		TransactionOperationNotFoundException thrownException = assertThrows(
				TransactionOperationNotFoundException.class,
				() -> transactionProcessorFactory.getProcessor(operationType));

		assertEquals("Operation type not found. Operation: " + operationType, thrownException.getMessage());
	}

	@Test
	void shouldThrowExceptionForNullOperationType() {
		String operationType = null;

		TransactionOperationNotFoundException thrownException = assertThrows(
				TransactionOperationNotFoundException.class,
				() -> transactionProcessorFactory.getProcessor(operationType));

		assertEquals("Operation type not found. Operation: null", thrownException.getMessage());
	}
}// end of class
