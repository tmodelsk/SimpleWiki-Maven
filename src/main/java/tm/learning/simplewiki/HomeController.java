package tm.learning.simplewiki;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String pageName = "home";
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("message", "Some msg from Home Controller, Home=" + pageName );
		
		return "home";
	}
	
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String viewPage(Locale locale, Model model, @PathVariable("pageName") String pageName) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("message", "Some msg from Home Controller, page=" + pageName );
		
		return "home";
	}
	
	@RequestMapping(value = "/{pageName}/edit", method = RequestMethod.GET)
	public String editPage(Locale locale, Model model, @PathVariable("pageName") String pageName) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("message", "Edit "+pageName );
		
		return "home";
	}
	
}
