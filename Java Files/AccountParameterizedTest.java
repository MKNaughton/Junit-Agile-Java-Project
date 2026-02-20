package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AccountParameterizedTest {

	private Account account ;  // instance variable for the class being tested 
	
	@BeforeAll // runs once at the start confirming account parameterized tests has begun
	public static void beforeAll() {
		System.out.println("Starting Account Parameterized Tests");	
	}
	@BeforeEach // each test should be independent - fresh account is created for each test
	public void beforeEach() {
		//new instance of account is created before each test- reseting bobs starting balance - each test is independant principle
		account = new Account("bob", 500);
	}
	
	 @ParameterizedTest // makes it possible to run a test multiple times with different arguments
	  @ValueSource(doubles = {100.00,150.00,300.00})
	  public void testMultipleDeposit(double amount) {
		  double expected = 500 + amount;//starting balance and deposit
		  account.deposit(amount);
		  assertEquals(expected, account.getBalance(),"Balance after multiple deposit should be"+ expected);
	  }

	//CsvSource: allows you to express argument lists as comma-seperated values:withdrawAmount, expectedSuccess,expectedBalance.
	@ParameterizedTest// run multiple tests with differant arguments
	@CsvSource({"200,true,300","500,true,0","600,false,500"})  
	public void testwithdrawMultiple(double amount, boolean expectedResult, double expectedBalance) {
		boolean result = account.withdraw(amount);
		 assertEquals(expectedResult, result,"Withdraw"+amount+"should"+(expectedResult?"succeed" : "fail"));
		 assertEquals(expectedBalance, account.getBalance(),"Balance should be"+expectedBalance);
	}
	@AfterEach //runs after each test confirming test is complete
	public void afterEach() {
		System.out.println("Parameterized test is completed!");
	}
	
@AfterAll// runs after all tests confirming all tests are complete
public static void afterAll() {
	System.out.println("All account parameterized tests are completed!");
}
}
