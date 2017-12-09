package tm.learning.simplewiki.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.val;
import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;

public class WikiServImpTests_MainWikiExists {
	
	@Test
	public void rootUrl_ExpectedMainPageView() {
		val rootUri = PageUri.ROOT;
		val pageAndWiki = createWikiWithMainPage();
		val pageSrc = pageAndWiki.page();
		when(pageFinder.findWikiAndPage(rootUri)).thenReturn(pageAndWiki);
		
		val html = new Html(pageSrc.getBody());
		when(wikiHtmlTranslator.buildHtml(pageSrc)).thenReturn(html);
		
		val pr = wikiServ.getPageResult(rootUri);
		
		assertThat(pr).isNotNull();
		assertThat(pr.pageUri()).isEqualToComparingFieldByField(rootUri);
		assertThat(pr.name()).isEqualTo(pageSrc.getName());
		assertThat(pr.wikiHtml().toString()).isEqualTo(pageSrc.getBody());
		assertThat(pr.html()).isNotNull();
		assertThat(pr.wiki()).isEqualToComparingFieldByField(pageAndWiki.wiki());
		assertThat(pr.mode()).isEqualTo(PageMode.View);
	}
	
	@Test
	public void rootUrlEdit_ExpectedMainPageEdit() {
		val rootUri = PageUri.ROOT;
		val pageAndWiki = createWikiWithMainPage();
		val pageSrc = pageAndWiki.page();
		when(pageFinder.findWikiAndPage(rootUri)).thenReturn(pageAndWiki);
		
		val html = new Html(pageSrc.getBody());
		when(wikiHtmlTranslator.buildHtml(pageSrc)).thenReturn(html);
		
		val pr = wikiServ.getPageResult(rootUri, PageMode.Edit);
		
		assertThat(pr).isNotNull();
		assertThat(pr.pageUri()).isEqualToComparingFieldByField(rootUri);
		assertThat(pr.name()).isEqualTo(pageSrc.getName());
		assertThat(pr.wikiHtml().toString()).isEqualTo(pageSrc.getBody());
		assertThat(pr.html()).isNotNull();
		assertThat(pr.wiki()).isEqualToComparingFieldByField(pageAndWiki.wiki());
		assertThat(pr.mode()).isEqualTo(PageMode.Edit);
	}
	
	@Test
	public void unexistingPageUrl_ExpectedPageEdit() {
		val pageAndWiki = createWikiWithMainPage();
		val wikiOnly = new PageAndWiki(pageAndWiki.wiki(), null);
		val unexistingPagePrefixUrl = "somePagePrefixUrl";
		val unexistingUri = new PageUri(null, unexistingPagePrefixUrl);
		
		when(pageFinder.findWikiAndPage(PageUri.ROOT)).thenReturn(pageAndWiki);
		when(pageFinder.findWikiAndPage(unexistingUri)).thenReturn(wikiOnly);
		
		when(wikiHtmlTranslator.buildHtml(any(WikiHtml.class))).thenReturn(new Html("some examplemocked content"));

		val pr = wikiServ.getPageResult(unexistingUri);
		
		assertThat(pr).isNotNull();
		assertThat(pr.pageUri()).isEqualToComparingFieldByField(unexistingUri);
		assertThat(pr.mode()).isEqualTo(PageMode.Edit); 
		assertThat(pr.name()).isEqualTo(unexistingPagePrefixUrl);
		assertThat(pr.html()).isNotNull();
		assertThat(pr.wikiHtml()).isNotNull();
	}
	
	@Test
	public void unexistingPageUrlView_ExpectedPageEdit() {
		val pageAndWiki = createWikiWithMainPage();
		val wikiOnly = new PageAndWiki(pageAndWiki.wiki(), null);
		val unexistingPagePrefixUrl = "somePagePrefixUrl";
		val unexistingUri = new PageUri(null, unexistingPagePrefixUrl);
		
		when(pageFinder.findWikiAndPage(PageUri.ROOT)).thenReturn(pageAndWiki);
		when(pageFinder.findWikiAndPage(unexistingUri)).thenReturn(wikiOnly);
		
		when(wikiHtmlTranslator.buildHtml(any(WikiHtml.class))).thenReturn(new Html("some examplemocked content"));

		val pr = wikiServ.getPageResult(unexistingUri, PageMode.View);
		
		assertThat(pr).isNotNull();
		assertThat(pr.pageUri()).isEqualToComparingFieldByField(unexistingUri);
		assertThat(pr.mode()).isEqualTo(PageMode.Edit); 
		assertThat(pr.name()).isEqualTo(unexistingPagePrefixUrl);
		assertThat(pr.html()).isNotNull();
		assertThat(pr.wikiHtml()).isNotNull();
	}
	
	
	private PageAndWiki createWikiWithMainPage() {
		val wiki = new Wiki("wiki", "wiki", null);
		val page = new Page("main", null, "main page body");
		wiki.addPage(page);
		
		return new PageAndWiki(wiki, page);
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
