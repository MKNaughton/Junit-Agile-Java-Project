package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class BankingAppParameterizedTest {
	private BankingApp bank;// bank instance to test
	
	@BeforeAll
	public static void beforeAll() {
		System.out.println("Starting Banking App parameterized tests");
	}
	
	@BeforeEach// create  new bank before each test
	public void beforeEach() {
		bank = new BankingApp();
		bank.addAccount("Alice", 1000);//alice bank balnce should reset to starting balance of 1000
	}
	
	
	
	 @ParameterizedTest
	 @ValueSource(doubles = {100.0, 250.0, 500.0, 1000.0})//test deposits with multiple values using @valueSource -speciefy a string arrays the source of arguments
	 public void testDepositMultiple(double depositAmount) {
		 double expectedBalance = 1000+ depositAmount;//starting balance+deposit.
		 bank.deposit("Alice", depositAmount);
		 assertEquals(expectedBalance, bank.getBalance("Alice"),"Balance after the deposit should be"+expectedBalance);
		 
	 }
		
	//@CsvSource allows you to express argumentlists as coma-separated values 
	 //test withdrawls with multiple values and expected results - each row withdraAmount, expectedSuccess, expectedBalance.
	 
	 @ParameterizedTest
	 @CsvSource({
		 "200, true, 800","1000, true,0","1500, false, 1000","2000, false, 1000"
	 })	
	 public void testWithdrawlMultiple(double amount,boolean expectedSuccess,double expectedBalance) {
		boolean result = bank.withdraw("Alice",amount);
		assertEquals(expectedSuccess,result, "withdraw"+ amount+"should"+(expectedSuccess?"succeed":"fail"));
		assertEquals(expectedBalance, bank.getBalance("Alice"),"Balance should be"+expectedBalance );
	 }
		
		//Test loan approval with multiple amounts Using CsvSource to test various loan amounts -confirm set to correct amounts.
		
	 @ParameterizedTest
	 @CsvSource({"500,500", "1000,1000"})
		public void testApprovelLoanWithMultiple(double loanAmount, double expectedLoan) {
		 bank.approveLoan("Alice", loanAmount);
		 assertEquals(expectedLoan,bank.getLoan("Alice"),"Loan amount should be"+expectedLoan+"after approval");
	 }}
