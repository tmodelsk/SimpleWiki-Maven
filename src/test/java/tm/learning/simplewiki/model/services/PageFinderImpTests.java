package tm.learning.simplewiki.model.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.val;
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.model.repo.base.PageDao;
import tm.learning.simplewiki.model.repo.base.WikiDao;
import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.repo.data.Wiki;
import tm.learning.simplewiki.model.services.PageFinderImp;

public class PageFinderImpTests {
	@Test
	public void mainWikiExists_rootUrl_ExpectedMainWIkiMainPage() {

		val rootUri = PageUri.ROOT;
		val wiki = new Wiki("wiki", "wiki", null);
		val page = new Page("main", null, "main page body");
		wiki.addPage(page);
		
		when(wikiDao.findByUrlPrefix(null)).thenReturn(wiki);
		when(pageDao.findPage(null, null)).thenReturn(page);
		
		val res = wikiServ.findWikiAndPage(rootUri);
		
		assertThat(res).isNotNull();
		assertThat(res.wiki()).isNotNull();
		assertThat(res.page()).isNotNull();
	}

	@Test
	public void mainWikiExists_unexistingPageUrl_ExpectedMainWikiEditPage() {
		val wiki = new Wiki("wiki", "wiki", null);
		
		when(wikiDao.findByUrlPrefix(null)).thenReturn(wiki);
		
		val pageUrl = "someStrangeUnexistingPage";
		val pageUri = new PageUri(null, pageUrl);
		val res = wikiServ.findWikiAndPage(pageUri);
		
		assertThat(res).isNotNull();
		assertThat(res.wiki()).isNotNull();
		assertThat(res.page()).isNull();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	private PageFinderImp wikiServ;
	
	@Mock
	private WikiDao wikiDao;
	@Mock
	private PageDao pageDao;
}
