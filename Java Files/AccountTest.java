package ie.atu.dip;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.concurrent.TimeUnit;
// timeout to ensure that a method executes for a set period of time and if not it fails.
@Timeout(value = 300, unit = TimeUnit.MILLISECONDS)//Annotation that sets a 300 millisecond time limit for all test methods in the class
class AccountTest {

	//instance variable for the class that is being tested
	
	private static Account account;// static means this variable or class only has one instance.
	
	@BeforeAll // runs once before the class starts-set up : confirms account tests are starting  
	public static void BeforeAll() {
		System.out.println("Starting Account Tests");
	}
	
	@BeforeEach
	public void BeforeEach() {// runs before each test method - creating fresh bob account to test
		//new instance of account before each test with test data
		account = new Account("Bob", 500);// starting balance and account holder to test 
		
	}
	//Test method for getAccountHolder
	//test expected results from known input using assert Equals to compare two values = expected and actual
	@Test
	public void testGetAccountHolder()
	{
	assertEquals("Bob", account.getAccountHolder(),"Account holder should be Bob");	
	}	

	//Test method for getBalance
  @Test
  public void testGetBalance() {
	  assertEquals(500,account.getBalance(),"Starting balance should be 500 ");
	  
  }
  
//Test method for deposit
  @Test
  public void testDeposit() {
	  account.deposit(250); // Deposits 250 into bobs account
	  assertEquals(750, account.getBalance(),"After deposit balance should be 750 ");//expected balance after deposit= deposit + bob starting balance
  }
   //Test method for withdrawal success
  @Test
  public void testWithdrawalSuccessful() {
	  
	  boolean result = account.withdraw(350);
	  assertTrue(result,"withdrawl should be succesfull");//checks expected true results as 350 is less than 500 expected balance is now 150
	  assertEquals(150,account.getBalance(),"After withdrawl expected balance should be 150");
  }
  
  //Test method for unsuccessful withdrawl : insufficient funds - withdral is larger than balance
  //boundary analysis =>test the boundary of what is allowed/successful 
  @Test
  public void testWithdrawInsufficientFunds() {
	  boolean result = account.withdraw(1000);
	  assertFalse(result,"Withdrawl should fail with insufficient funds");//expected result = insufficient funds  
	  assertEquals(500,account.getBalance(),"Balance is 500");// expected bank balance should remain the same and stay on starting balance of 500
	  
  }
  
  //Test method for approved Loan
  @Test
  public void testApproveLoan() {
	  account.approveLoan(2000);// a loan of 2000 is approved for bob 
	  assertEquals(2000, account.getLoan(),"Loan Approvel amount 2000");
  } 
  
  
  //Test for loan repayment succesfull.
  @Test
  public void testRepayLoanSuccessful() {
	  account.approveLoan(2000);
	  boolean result = account.repayLoan(500);
	  assertTrue(result, "Repayment should be succesfull if below loan amount");// repayment of 500 is valid so expected so repayment is succesfull
	  assertEquals(1500, account.getLoan(), "Loan remayment amount should be now 1500 ");// expected loan balance is reset: the loan balance - succesfull repayment amount 
  }
  
  
  //Test method for loan repayment unsuccessful
  @Test
  public void testRepaymentLoanUnsuccessfull() {
	  account.approveLoan(2000); //call the approved loan amount.
	  boolean result = account.repayLoan(2500); // repayment amount is larger than the loan amount
	  assertFalse(result, "Repayment should fail if more than loan amount");//Testing boundary 
	  assertEquals(2000, account.getLoan(), "Loan repayment amount remains");
 
  }
  //Better applied as annotation then to indiviual test s as all should really timeout after a certain amount of time
 // @Test
//  @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)// timeout gives the ability to ensure that a method executes for a set period of time and if not it fails.
//  public void testDepositTimeout() {
//	  account.deposit(100);
//	  assertEquals(600, account.getBalance(),"Balance should be 600");
  
  
  //@Disabled
 
//NullPointerException - to test when account data is entered but does not exist
@Test
public void testNullPointerException() {
	assertThrows(NullPointerException.class, () -> {// throws the expected exception
	Account nullAccount = null;// if an account is null name and so points to a null balance as account does not exist
	nullAccount.getBalance();// throws NullPointerException - test will fail as nothing exists 
	});
	System.out.println("nullPointerException - account does not exist");
}
//ArithmetricException- tests when a calculation fails - such as divding by 0
@Test
public void testArithmetricException() {
	assertThrows(ArithmeticException.class, () ->{
		int result = 10/0;//division by zero throw ArithmetricException	
	});
	System.out.println("ArithmetricExceptionException - cannot divide by zero");
}

  @AfterEach// runs after each individual test.
  public void afterEach() {
	  System.out.println("Test completed");
  }
  
    @AfterAll // runs once after all tests are completed.
  
  public static void afterAll() {
	  
	  System.out.println("All Account Tests have been completed!");
  }
  
  
  
  
}
