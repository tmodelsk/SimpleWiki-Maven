package tm.learning.simplewiki;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tm.learning.simplewiki.controllers.HomeControllerMainWikiTests;
import tm.learning.simplewiki.model.services.PageFinderImpTests;
import tm.learning.simplewiki.model.services.WikiServImpTests_MainWikiExists;
import tm.learning.simplewiki.model.services.WikiServImpTests_MainWikiNotExists;

@RunWith(Suite.class)
@SuiteClasses({ 
	HomeControllerMainWikiTests.class, 
	WikiServImpTests_MainWikiExists.class, WikiServImpTests_MainWikiNotExists.class, 
	PageFinderImpTests.class 
})
public class AllTests {

}
