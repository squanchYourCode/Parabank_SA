import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(ParasoftImpactedTestSuiteRunner.class)
@SuiteClasses(
    {
        com.parasoft.parabank.web.controller.RestServiceProxyControllerSpringTest.class
    }
)
@SuiteDescription("Parasoft impacted tests run from parabank")
public class ParasoftImpactedTestSuite
{
    public static Map<String, Set<String>> getMethods()
    {
        return new HashMap<String, Set<String>>()
        {
            {
                put("com.parasoft.parabank.web.controller.RestServiceProxyControllerSpringTest", new TreeSet<String>(Arrays.asList(new String[] { "testCreateAccount", "testGetAccount", "testGetAccounts", "testGetTransaction", "testGetTransactions", "testGetTransactionsByAmount", "testGetTransactionsOnDate", "testRequestLoan", "testTransferAndGetTransactionByMonthAndType" })));
            }
        };
    }
}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface SuiteDescription {
    String value();
}