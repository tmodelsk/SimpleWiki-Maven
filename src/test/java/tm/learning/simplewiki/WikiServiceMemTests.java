package tm.learning.simplewiki;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.val;
import tm.learning.simplewiki.model.WikiServiceImp;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.model.repo.PageDao;
import tm.learning.simplewiki.model.repo.WikiDao;

public class WikiServiceMemTests {
	
	@Test
	public void mainWikiExists_rootUrl_ExpectedMainWIkiMainPage() {

		val wiki = new Wiki("wiki", "wiki", null);
		val page = new Page("main", null, "main page body");
		wiki.getPages().add(page);
		
		wikiServ.ensureInitialized();
		
		when(wikiDao.findByUrlPrefix(null)).thenReturn(wiki);
		when(pageDao.findPage(null, null)).thenReturn(page);
		
		val res = wikiServ.getWikiAndPage(null, null);
		
		assertThat(res).isNotNull();
		assertThat(res.wiki()).isNotNull();
		assertThat(res.page()).isNotNull();
	}

	@Test
	public void mainWikiExists_unexistingPageUrl_ExpectedMainWikiEditPage() {
		val wiki = new Wiki("wiki", "wiki", null);
		
		wikiServ.ensureInitialized();
		
		when(wikiDao.findByUrlPrefix(null)).thenReturn(wiki);
		
		val pageUrl = "someStrangeUnexistingPage";
		val res = wikiServ.getWikiAndPage(null, pageUrl);
		
		assertThat(res).isNotNull();
		assertThat(res.wiki()).isNotNull();
		assertThat(res.page()).isNull();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	private WikiServiceImp wikiServ;
	
	@Mock
	private WikiDao wikiDao;
	@Mock
	private PageDao pageDao;
	
}
