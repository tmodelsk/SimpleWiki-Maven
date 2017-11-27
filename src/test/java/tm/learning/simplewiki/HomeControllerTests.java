package tm.learning.simplewiki;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.ResultActions;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import tm.learning.simplewiki.controllers.HomeController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

public class HomeControllerTests {
		
	@Test
	public void rootUrl_ExpectedViewMainPage() throws Exception {
		mockMvc.perform(get("/", 1l))
			.andExpect(status().isOk())
			.andExpect(view().name("view"))
			.andExpect(model().attributeExists("page"));
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

	@InjectMocks 
	HomeController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this); 
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}

	
}
