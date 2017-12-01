package tm.learning.simplewiki;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HomeControllerTests.class, WikiServiceMemTests.class })
public class AllTests {

}
