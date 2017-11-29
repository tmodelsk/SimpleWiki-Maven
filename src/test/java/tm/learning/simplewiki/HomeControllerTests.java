package tm.learning.simplewiki;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.ResultActions;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import lombok.val;
import tm.learning.simplewiki.controllers.HomeController;
import tm.learning.simplewiki.model.PageResult;
import tm.learning.simplewiki.model.SimpleWikiBaseEx;
import tm.learning.simplewiki.model.WikiService;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import java.lang.reflect.Executable;

public class HomeControllerTests {
		
	
	@Test
	public void mainWikimainPageExist_rootUrl_ExpectedViewMainPage() throws Exception {
		
		val wiki = new Wiki("main wiki", "desc", null);
		val mainPage = new Page("Home", null, "some html");
		val wikiResult = new PageResult(wiki, mainPage);
		
		when(wikiService.getWikiAndPage(null, null)).thenReturn(wikiResult);
		
		val result =  mockMvc.perform(get("/", 1l));
		checkBasic(result, "view");
	}
	
	@Test
	public void mainWikiNotExists_rootUrl_ExpectedException() throws Exception {
		val wikiResult = new PageResult(null, null);
		
		when(wikiService.getWikiAndPage(null, null)).thenReturn(wikiResult);
		
		assertThatThrownBy(() -> mockMvc.perform(get("/", 1l))).hasCauseInstanceOf(SimpleWikiBaseEx.class);
	
	}
	
	@Test
	public void rootEditUrl_ExpectedEditViewMainPage() throws Exception {
		mockMvc.perform(get("?edit", 1l))
			.andExpect(status().isOk())
			.andExpect(view().name("edit"))
			.andExpect(model().attributeExists("page"));
	}
	
	@Test
	public void existingPageUrl_ExpectedViewPage() throws Exception {
		mockMvc.perform(get("/someExistingPage", 1l))
			.andExpect(status().isOk())
			.andExpect(view().name("view"))
			.andExpect(model().attributeExists("page"));
	}
	
	@Test
	public void existingPageEditUrl_ExpectedViewPage() throws Exception {
		mockMvc.perform(get("/someExistingPage?edit=a", 1l))
			.andExpect(status().isOk())
			.andExpect(view().name("edit"))
			.andExpect(model().attributeExists("page"));
	}

	private void checkBasic(ResultActions result, String viewExpected) throws Exception {
		
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
