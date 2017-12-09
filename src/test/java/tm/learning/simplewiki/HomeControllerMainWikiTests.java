package tm.learning.simplewiki;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
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
import tm.learning.simplewiki.commons.SimpleWikiBaseEx;
import tm.learning.simplewiki.controllers.HomeController;
import tm.learning.simplewiki.model.PageAndWiki;
import tm.learning.simplewiki.model.WikiService;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.views.Views;

public class HomeControllerMainWikiTests {
	
	@Test
	public void mainPageExist_rootUrl_ExpectedViewMainPage() throws Exception {
		
		val wiki = new Wiki("main wiki", "desc", null);
		val mainPage = new Page("Home", null, "some html");
		wiki.addPage(mainPage);
		val wikiResult = new PageAndWiki(wiki, mainPage);
		
		when(wikiService.findWikiAndPage(null, null)).thenReturn(wikiResult);
		
		performGetCheck("/", Views.PAGE_VIEW, mainPage.getName());
	}
	
	@Test
	public void mainWikiNotExists_rootUrl_ExpectedException() throws Exception {
		val wikiResult = new PageAndWiki(null, null);
		
		when(wikiService.findWikiAndPage(null, null)).thenReturn(wikiResult);
		
		assertThatThrownBy(() -> mockMvc.perform(get("/", 1l))).hasCauseInstanceOf(SimpleWikiBaseEx.class);
	
	}
	
	@Test
	public void rootEditUrl_ExpectedEditViewMainPage() throws Exception {
		
		val wiki = new Wiki("main wiki", "desc", null);
		val mainPage = new Page("Home", null, "some html");
		val wikiResult = new PageAndWiki(wiki, mainPage);
		
		when(wikiService.findWikiAndPage(null, null)).thenReturn(wikiResult);
		
		performGetCheck("?edit", Views.PAGE_EDIT, mainPage.getName());
	}
	
	@Test
	public void existingPageUrl_ExpectedViewPage() throws Exception {
		
		val wiki = new Wiki("main wiki", "desc", null);
		val mainPage = new Page("Home", null, "some html");
		val somePage = new Page("Some page", "someExistingPage" , "some page html");
		
		val wikiMainResult = new PageAndWiki(wiki, mainPage);
		val wikiSomePageResult = new PageAndWiki(wiki, somePage);
	
		when(wikiService.findWikiAndPage(null, null)).thenReturn(wikiMainResult);
		when(wikiService.findWikiAndPage(null, "someExistingPage")).thenReturn(wikiSomePageResult);
		
		performGetCheck("/"+somePage.getUrlPrefix(), Views.PAGE_VIEW, somePage.getName());
	}
	
	@Test
	public void existingPageEditUrl_ExpectedEditPage() throws Exception {
		val wiki = new Wiki("main wiki", "desc", null);
		val mainPage = new Page("Home", null, "some html");
		val somePage = new Page("Some page", "someExistingPage" , "some page html");
		
		val wikiMainResult = new PageAndWiki(wiki, mainPage);
		val wikiSomePageResult = new PageAndWiki(wiki, somePage);
	
		when(wikiService.findWikiAndPage(null, null)).thenReturn(wikiMainResult);
		when(wikiService.findWikiAndPage(null, "someExistingPage")).thenReturn(wikiSomePageResult);
		
		
		performGetCheck("/"+somePage.getUrlPrefix()+"?edit", Views.PAGE_EDIT, somePage.getName());
	}

	@Test
	public void nonExistingPageUrl_ExpectedEditPage() throws Exception {
		val wiki = new Wiki("main wiki", "desc", null);
		val mainPage = new Page("Home", null, "some html");
		val somePage = new Page("Some page", "someExistingPage" , "some page html");
		
		val wikiMainResult = new PageAndWiki(wiki, mainPage);
		val wikiSomePageResult = new PageAndWiki(wiki, somePage);
	
		val unexistingPage = "someUnexistingPage";
		when(wikiService.findWikiAndPage(null, null)).thenReturn(wikiMainResult);
		when(wikiService.findWikiAndPage(null, "someExistingPage")).thenReturn(wikiSomePageResult);
		when(wikiService.findWikiAndPage(null, unexistingPage)).thenReturn(new PageAndWiki(wiki, null));
		
		performGetCheck("/"+unexistingPage, Views.PAGE_EDIT, unexistingPage);
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
