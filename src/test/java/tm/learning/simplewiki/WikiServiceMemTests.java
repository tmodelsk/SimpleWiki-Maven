package tm.learning.simplewiki;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import tm.learning.simplewiki.model.WikiServiceMem;

public class WikiServiceMemTests {
	
	@Test
	public void mainWikiExists_rootUrl_ExpectedMainWIkiMainPage() {
		val wikiServ = new WikiServiceMem(false);
		wikiServ.ensureInitialized();
		
		val res = wikiServ.getWikiAndPage(null, null);
		
		assertThat(res).isNotNull();
		assertThat(res.wiki()).isNotNull();
		assertThat(res.page()).isNotNull();
	}

	@Test
	public void mainWikiExists_unexistingPageUrl_ExpectedMainWikiEditPage() {
		val wikiServ = new WikiServiceMem(false);
		wikiServ.ensureInitialized();
		
		val pageUrl = "someStrangeUnexistingPage";
		val res = wikiServ.getWikiAndPage(null, pageUrl);
		
		assertThat(res).isNotNull();
		assertThat(res.wiki()).isNotNull();
		assertThat(res.page()).isNull();
	}
	
}
