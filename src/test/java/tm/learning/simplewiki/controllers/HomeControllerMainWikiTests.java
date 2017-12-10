package tm.learning.simplewiki.controllers;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.ArgumentMatchers.any;
//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.ResultActions;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import lombok.val;
import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.controllers.HomeController;
import tm.learning.simplewiki.model.services.PageMode;
import tm.learning.simplewiki.model.services.PageResult;
import tm.learning.simplewiki.model.services.WikiResult;
import tm.learning.simplewiki.model.services.WikiService;
import tm.learning.simplewiki.views.Views;

public class HomeControllerMainWikiTests {
	
	@Test
	public void rootUrl_ExpectedMainPageView() throws Exception {
		
		val html = "some html";
		val pr = new PageResult();
		
		pr.pageUri(PageUri.ROOT);
		pr.name("Home");
		pr.wikiHtml(new WikiHtml(html));
		pr.html(new Html(html));
		pr.mode(PageMode.View);
		
		val wr = new WikiResult();
		wr.name("main wiki");
		pr.wiki(wr);
		
		when(wikiService.getPageResult(PageUri.ROOT, PageMode.View)).thenReturn(pr);
		
		performGetCheck("/", Views.PAGE_VIEW, pr.name());
	}
	
	@Test
	public void rootEditUrl_ExpectedEditViewMainPage() throws Exception {
		
		val html = "some html";
		val pr = new PageResult();
		
		pr.pageUri(PageUri.ROOT);
		pr.name("Home");
		pr.wikiHtml(new WikiHtml(html));
		pr.html(new Html(html));
		pr.mode(PageMode.View);
		
		val wr = new WikiResult();
		wr.name("main wiki");
		pr.wiki(wr);
		
		when(wikiService.getPageResult(any(PageUri.class), any(PageMode.class))).thenReturn(pr);
		
		performGetCheck("?edit", Views.PAGE_EDIT, pr.name());
	}
	
	@Test
	public void existingPageUrl_ExpectedViewPage() throws Exception {
		val someUrlPrefix = "somePrefix";
		val prUri = new PageUri(null, someUrlPrefix);
		
		val html = "some html";
		val pr = new PageResult();
		
		pr.pageUri(prUri);
		pr.name("Home");
		pr.wikiHtml(new WikiHtml(html));
		pr.html(new Html(html));
		pr.mode(PageMode.View);
		
		val wr = new WikiResult();
		wr.name("main wiki");
		pr.wiki(wr);
		
		when(wikiService.getPageResult(prUri, PageMode.View)).thenReturn(pr);
		
		performGetCheck("/"+prUri.pagePrefix(), Views.PAGE_VIEW, pr.name());
	}
	
	@Test
	public void existingPageEditUrl_ExpectedEditPage() throws Exception {
		val someUrlPrefix = "somePrefix";
		val prUri = new PageUri(null, someUrlPrefix);
		
		val html = "some html";
		val pr = new PageResult();
		
		pr.pageUri(prUri);
		pr.name("Home");
		pr.wikiHtml(new WikiHtml(html));
		pr.html(new Html(html));
		pr.mode(PageMode.Edit);
		
		val wr = new WikiResult();
		wr.name("main wiki");
		pr.wiki(wr);
		
		when(wikiService.getPageResult(prUri, PageMode.View)).thenReturn(pr);
		
		
		performGetCheck("/"+prUri.pagePrefix()+"?edit", Views.PAGE_EDIT, pr.name());
	}

	@Test
	public void nonExistingPageUrl_ExpectedEditPage() throws Exception {
		val someUrlPrefix = "somePrefix";
		val prUri = new PageUri(null, someUrlPrefix);
		
		val html = "some html";
		val pr = new PageResult();
		
		pr.pageUri(prUri);
		pr.name("Home");
		pr.wikiHtml(new WikiHtml(html));
		pr.html(new Html(html));
		pr.mode(PageMode.Edit);
		
		val wr = new WikiResult();
		wr.name("main wiki");
		pr.wiki(wr);
		
		when(wikiService.getPageResult(prUri, PageMode.View)).thenReturn(pr);
		
		performGetCheck("/"+prUri.pagePrefix(), Views.PAGE_EDIT, pr.name());
	}
	
	private void performGetCheck(String url, String viewExpected, String pageNameExpected) throws Exception {
		val result =  mockMvc.perform(get(url, 1l));
		check(viewExpected, pageNameExpected, result);
	}
	private void check(String viewExpected, String pageNameExpected, ResultActions resultAction) throws Exception {
		checkView(viewExpected, resultAction);
		checkPageName(pageNameExpected, resultAction);
	}
	@SuppressWarnings("unchecked")
	private void checkPageName(String expected, ResultActions resultAction) throws Exception {
		resultAction.andExpect(model().attribute("page", allOf(hasProperty("name", is(expected)))));
	}
	private void checkView(String viewExpected, ResultActions result) throws Exception {
		
		result.andExpect(status().isOk())
		.andExpect(view().name(viewExpected));
		
		result.andExpect(model().attributeExists("page"));
		result.andExpect(model().attributeExists("wiki"));
	}
	
	@Mock
	private WikiService wikiService;
	
	@InjectMocks 
	private HomeController controller;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this); 
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}

	
}
