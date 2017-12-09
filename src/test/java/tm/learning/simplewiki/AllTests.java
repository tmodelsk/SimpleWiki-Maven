package tm.learning.simplewiki;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tm.learning.simplewiki.model.PageFinderImpTests;
import tm.learning.simplewiki.model.WikiServImpTests_MainWikiExists;
import tm.learning.simplewiki.model.WikiServImpTests_MainWikiNotExists;

@RunWith(Suite.class)
@SuiteClasses({ 
	HomeControllerMainWikiTests.class, 
	WikiServImpTests_MainWikiExists.class, WikiServImpTests_MainWikiNotExists.class, 
	PageFinderImpTests.class 
})
public class AllTests {

}
