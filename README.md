# Banking Test Suite with JUnit 5

## Description
This is a Java banking test suite built using JUnit 5 to validate account and bank management functionality. The project refactors a provided banking scenario from a monolithic design with a nested private Account class into separate public classes following the single responsibility principle. The test suite validates deposits, withdrawals, loan approvals, loan repayments, and total deposit tracking through standard unit tests, parameterized tests, exception tests, and test lifecycle management.

The banking scenario manages accounts with account holder names, balances, and loan amounts. Operations include adding accounts, depositing funds, withdrawing funds with insufficient funds checking, approving loans based on available bank deposits, and repaying loans. All functionality is validated through comprehensive JUnit 5 tests using assertions, parameterized testing with multiple data sets, exception handling, and timeout constraints.

## Project Overview

This test suite validates banking operations through JUnit 5 framework. The original code had Account as a private nested class inside BankingApp. Refactoring extracted Account to a separate public class in the ie.atu.dip package, improving testability and following single responsibility principle. Tests use @BeforeEach to create fresh test data before each test ensuring independence. Parameterized tests with @ValueSource and @CsvSource reduce code duplication. Exception tests validate NullPointerException and ArithmeticException handling. Test suite combines core tests using @Suite and @SelectClasses for batch execution.

## Technologies

- **Language:** Java 8
- **Testing Framework:** JUnit 5.13.4
- **IDE:** Eclipse 2024
- **Build System:** Eclipse Java Project
- **Package:** ie.atu.dip

## System Architecture

The project is organised across seven classes with clear separation of concerns:

![UML Class Diagram](JunitAgile%20uml.png)

### Core Components

- **BankingApp** - Manages multiple accounts via ArrayList. Handles account creation, deposits, withdrawals, loan approvals, loan repayments, and total deposit tracking. Contains findAccount() helper method for account lookup and hasAccount() for existence checking. Main method demonstrates hardcoded scenarios with Alice and Bob accounts.
- **Account** - Represents individual accounts with account holder name, balance, and loan amount. Refactored from private nested class to separate public class. Handles deposit, withdrawal with insufficient funds checking, loan approval, and loan repayment with validation.
- **BankingAppTest** - Tests bank management using Alice (1000) and Bob (500) test accounts. Validates account creation, hasAccount() lookup, deposits updating both account and bank totals, withdrawals, loan approvals with insufficient/sufficient funds, loan repayments, and exception handling.
- **AccountTest** - Tests account behavior using Bob account with 500 starting balance. Validates getters, deposits, withdrawals, loan operations, and exceptions. Class-level @Timeout(300 milliseconds) enforces performance constraints.
- **BankingAppParameterizedTest** - Parameterized tests for bank operations using Alice account. Tests multiple deposit amounts with @ValueSource, withdrawal scenarios with @CsvSource (amount, success, balance), and loan approvals.
- **AccountParameterizedTest** - Parameterized tests for account operations using Bob account. Tests deposit amounts with @ValueSource and withdrawal scenarios with @CsvSource testing boundary cases.
- **AllTestSuite** - Combines AccountTest and BankingAppTest using @Suite and @SelectClasses for batch execution. Parameterized tests run separately.

### Key Design Decisions

- **Refactoring** - Account extracted from private nested class to separate public class eliminating code smell and improving testability.
- **Test Isolation** - AccountTest tests account behavior independently, BankingAppTest tests bank management separately. @BeforeEach creates fresh test data.
- **Helper Methods** - findAccount() encapsulates lookup logic iterating through accounts list. hasAccount() returns boolean for existence checking.
- **Loan Business Rules** - Loan approvals limited by total bank deposits. Approvals decrease bank deposits, repayments increase deposits.
- **Hardcoded Expected Values** - Tests use hardcoded values because you control the setup. Tests verify implementation correctness, not reproduce logic.

## Features

### Banking Operations
- Add accounts with initial deposit
- Deposit with validation
- Withdraw with insufficient funds checking
- Approve loans up to bank deposit limit
- Repay loans with overpayment prevention
- Track total deposits
- Check account existence with hasAccount()

### JUnit 5 Testing
- Standard unit tests with assertEquals, assertTrue, assertFalse
- Parameterized tests with @ValueSource (single parameters)
- Parameterized tests with @CsvSource (multiple parameters as comma-separated values)
- Exception testing with assertThrows for NullPointerException and ArithmeticException
- Test lifecycle with @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
- Class-level timeout with @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
- Test suite with @Suite and @SelectClasses
- Boundary testing for withdrawal and loan limits

### TDD Practices
- Red-green-refactor workflow
- Code smell elimination
- Regression testing after refactoring
- Test-first development

## Getting Started

git clone https://github.com/MKNaughton/JunitAgileReadMe.git
cd JunitAgileReadMe

### Prerequisites
- Java Development Kit (JDK 8 or higher)
- Eclipse IDE 2024
- JUnit 5.13.4 library

### Running the Application

**Eclipse IDE:**
1. Right-click on `BankingApp.java`
2. Select **Run As → Java Application**
3. View console output with Alice and Bob scenarios

**Command Line:**
```bash
cd /path/to/project/src
javac ie/atu/dip/BankingApp.java ie/atu/dip/Account.java
java ie.atu.dip.BankingApp
```

### Running the Tests

**Run Test Suite:**
1. Right-click on `AllTestSuite.java`
2. Select **Run As → JUnit Test**

**Run Individual Tests:**
- Right-click test class (AccountTest, BankingAppTest, parameterized tests)
- Select **Run As → JUnit Test**

## Implementation Details

### Account Class Structure

Account refactored as separate public class:
```java
package ie.atu.dip;

public class Account {
    private String accountHolder;
    private double balance;
    private double loan;

    public Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.loan = 0;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }
}
```

### Parameterized Testing with @ValueSource

Tests multiple values with single test method:
```java
@ParameterizedTest
@ValueSource(doubles = {100.0, 250.0, 500.0, 1000.0})
public void testDepositMultiple(double depositAmount) {
    double expectedBalance = 1000 + depositAmount;
    bank.deposit("Alice", depositAmount);
    assertEquals(expectedBalance, bank.getBalance("Alice"));
}
```

### Parameterized Testing with @CsvSource

Tests multiple parameters as comma-separated values:
```java
@ParameterizedTest
@CsvSource({
    "200, true, 800",
    "1000, true, 0",
    "1500, false, 1000"
})
public void testWithdrawlMultiple(double amount, boolean expectedSuccess, double expectedBalance) {
    boolean result = bank.withdraw("Alice", amount);
    assertEquals(expectedSuccess, result);
    assertEquals(expectedBalance, bank.getBalance("Alice"));
}
```

### Exception Testing

Validates proper exception handling:
```java
@Test
public void testNullPointerException() {
    assertThrows(NullPointerException.class, () -> {
        Account nullAccount = null;
        nullAccount.getBalance();
    });
}
```

## Author

Marykerin Naughton
