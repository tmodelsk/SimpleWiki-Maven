package tm.learning.simplewiki.model.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.val;
import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.commons.SimpleWikiBaseEx;
import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.services.PageAndWiki;
import tm.learning.simplewiki.model.services.PageFinder;
import tm.learning.simplewiki.model.services.WikiHtmlTranslator;
import tm.learning.simplewiki.model.services.WikiServiceImp;

import static org.mockito.ArgumentMatchers.*;

public class WikiServImpTests_MainWikiNotExists {

	@Test
	public void rootUrl_ExpectedMainPageView() {
		val rootUri = PageUri.ROOT;
		val pageAndWiki = new PageAndWiki();
		when(pageFinder.findWikiAndPage(any(PageUri.class))).thenReturn(pageAndWiki);
		
		when(wikiHtmlTranslator.buildHtml(any(Page.class))).thenReturn(new Html("xxx"));
		
		assertThatThrownBy( () -> wikiServ.getPageResult(rootUri)).isInstanceOf(SimpleWikiBaseEx.class);
	}
	

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	private WikiServiceImp wikiServ;
	
	@Mock
	private PageFinder pageFinder;
	
	@Mock
	private WikiHtmlTranslator wikiHtmlTranslator;
}
