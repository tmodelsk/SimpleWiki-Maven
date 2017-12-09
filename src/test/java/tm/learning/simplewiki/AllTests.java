package tm.learning.simplewiki;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tm.learning.simplewiki.model.PageFinderImpTests;
import tm.learning.simplewiki.model.WikiServImpTests_MainWikiExists;

@RunWith(Suite.class)
@SuiteClasses({ HomeControllerMainWikiTests.class, WikiServImpTests_MainWikiExists.class, PageFinderImpTests.class })
public class AllTests {

}
