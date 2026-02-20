package ie.atu.dip;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({// Specifies which test classes to include in the suite
    AccountTest.class,  // tests all account class methosd
    BankingAppTest.class  // tests all bankingapp methods
})
public class AllTestSuite {// no code needed for body of class @suite and @SelectClasses call the methods in both classes 

}


