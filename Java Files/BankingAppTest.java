package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;


class BankingAppTest {
private static BankingApp bank;//static =>variable or class only has ever one instance.

    @BeforeAll // runs once before any tests execute
    public static void beforeAll() {
    	
    	System.out.println("Starting BankingApp Tests");
    }

	
	@BeforeEach//each unit test should be independent of each other, creates fresh data before each test for independence.
public void beforeEach() {
		bank= new BankingApp();
		bank.addAccount("Alice",1000);
		bank.addAccount("Bob",500);
		
	}
	
//Test - makes sure account was added checking account name and account balance.
	@Test
	public void testAccountCreation() {
		//check the account alice and account bob is true
		assertTrue(bank.hasAccount("Alice"),"Alice account should exist");
		assertTrue(bank.hasAccount("Bob"),"bob account should exist");
		//check alice account balance is equal to 1000, bob equals 500
		assertEquals(1000, bank.getBalance("Alice"),"Alice balance should be 1000");
		assertEquals(500, bank.getBalance("Bob"),"bob balance should be 500");
	}
	
	 // test the total account balances
	@Test  // test the total account balances 
	public void testGetTotalDeposits() {
		assertEquals(1500, bank.getTotalDeposits(),"Total deposits should be 1500(alice+bob)");
		
	}
	//TEST:Using alice bank account make a deposit through banking app
	//bank.deposit => updates account balance and banks total deposits
	//account.deposit() only updates the individual private account
 @Test
 public void testDepositBankingApp() {
	 
	 boolean result = bank.deposit("Alice",200);//deposit 200 into alice account through bankapp
	 //confirm it was successful
	 assertTrue(result,"Deposit should succeed");
	 //alice account balance should increase by deposit amount
	 assertEquals(1200,bank.getBalance("Alice"),"Alice balance should be 1200 after deposit 200");
	 //banks total deposit balance should increase from 1500 to 1700 
	 assertEquals(1700,bank.getTotalDeposits(),"Total bank deposits should be 1700 after deposit to alice account");
 }
	 //TEST: using bob bank account make a withdrawl
	 //bank.withdraw() : bobs account balance should decrease and so should the banks deposit total
	 //account.withdraw : privately updates just bobs account.
	 @Test
	 public void testWithdrawBankingApp(){
		 boolean result = bank.withdraw("Bob", 300);//withdraws 300 hundred
		 //check bobs account balance hase decreased to expected balance ->200
		 assertEquals(200,bank.getBalance("Bob"),"Bob balance should be 200 after withdral of 300");
		 //check banks total deposit balance has decreased to by 300
		 assertEquals(1200,bank.getTotalDeposits(),"Total deposits should be 1200 after withdrawl");
	 }
	 
	 //TEST:Loan approval based on banks total deposits, if bank has insufficient funds for loan amount 
	 @Test
	 public void testLoanApprovalInsufficientFunds() {
		 //banks total deposit balance is 1500 try approve loan for larger amount
		 boolean result = bank.approveLoan("Alice",3000);
		 //confirm loan is unsucessfull when insufficient funds
		 assertFalse(result,"Loan unsuccessful:insufficient funds");
		 //confirms alice loan is 0
		 assertEquals(0,bank.getLoan("Alice"),"Alice loan should be 0");
		 //confirm there has been no decrease in banks total deposit balance
		 assertEquals(1500,bank.getTotalDeposits(),"Total bank deposits should remain 1500");
		 
	 }
	 //TEST: test loan approval when bank has sufficient funds
	 @Test
	 public void testLoanApprovelSufficientFunds() {
		 boolean result = bank.approveLoan("Alice", 400);
		 //confirm loan was approved
		 assertTrue(result,"Loan should be approved when bank has sufficient funds");
		 //confirm alice loan amount is 400
		 assertEquals(400, bank.getLoan("Alice"),"Alice loan should be 400");
		 //confirm there has been a decrease in banks total balance 1500 - loan amount 400
		 assertEquals(1100,bank.getTotalDeposits(),"Total deposits should be 1100 after loan approval");
			 
	 }
	 
	 //TEST:that Loan Repayments updates banks totalDeposits when a repayment is made
	 @Test
	 public void testLoanRepayment() {
		 //loan of 400 is approved
		 bank.approveLoan("Alice",400);
		 //Loan repayment amount of 200 
		 boolean results = bank.repayLoan("Alice",200);
		 //confirm the repayemnt was successful
		 assertTrue(results,"Loan repayment should be succesfull");
		 //confirm remaining loan balance after repayments of 200 = 200
		 assertEquals(200,bank.getLoan("Alice"),"Alice loan balance should be 200 after repayment");
		 //confirm there has been a increase in banks total deposits balance from 1100 to 1300
		 assertEquals(1300,bank.getTotalDeposits(),"Total deposit balance should be 1300 after repayment");
		 
	 }
	 
//TEST:correct loan amount is returned after approval by looking up account name  
	 @Test
	 public void testGetLoan() {
		 //confirm loan amount is 0 before aproval of loan
		 assertEquals(0,bank.getLoan("Alice"),"Alice loan should be 0 before loan approval");
		 //loan amount is approved
		 bank.approveLoan("Alice",500);
		 //confirm getLoan returns the approved loan amount
		 assertEquals(500,bank.getLoan("Alice"), "Alice loan should be 500 after approvel");
	 }
	 
	//NullPointerException - to test when account data is entered but does not exist
	 @Test
	 public void testNullPointerException() {
	 	assertThrows(NullPointerException.class, () -> {// throws the expected exception
	 	Double balance = bank.getBalance("Sarah");// get Balance will return null because sarah account does not exist
	 	balance.doubleValue(); // try use the null value - nullexceptionthrown as sarah does not exist 
	 	});
	 	System.out.println("NullPointerException - cannot get balance as account does not exist");
	 }
	 //ArithmetricException- tests when a calculation fails - such as divding by 0
	 @Test
	 public void testArithmetricException() {
	 	assertThrows(ArithmeticException.class, () ->{
	 		int result = 10/0;//division by zero throw ArithmetricException	
	 	});
	 	System.out.println("ArithmeticException - bank cannot divide by zero");
	 }

 @AfterEach//runs every time after a test
 public void afterEach() {
	 System.out.println("Test has completed");
 }
 
 
 @AfterAll // runs after all test are completed
 public static void afterAll() {
	 System.out.println("All Bankingapp Test are complete");
 }
 
 
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 

