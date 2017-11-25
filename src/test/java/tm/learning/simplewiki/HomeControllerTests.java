package tm.learning.simplewiki;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.ResultActions;
import org.springframework.test.web.server.request.MockMvcRequestBuilders;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import lombok.Getter;
import lombok.val;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

public class HomeControllerTests {
	
	@InjectMocks 
	HomeController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this); 
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}
	
	@Getter
	String ccccc;
	
	@Test
	public void someTest() throws Exception {
		//Model modelMap = new Model();
		//String viewName = controller.homePage(null, model);
		
		// val perform = 
		mockMvc.perform(get("/", 1l))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
		
		
	}
	
	
}
