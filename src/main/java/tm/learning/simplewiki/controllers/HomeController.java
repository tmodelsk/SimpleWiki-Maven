package tm.learning.simplewiki.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;

import lombok.val;
import tm.learning.simplewiki.views.PageHtmlInfo;
import tm.learning.simplewiki.views.PageInfo;
import tm.learning.simplewiki.views.PageWHtmlInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private void addPageToModel(PageInfo pageData, Model model) {
		model.addAttribute("page", pageData );
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Locale locale, Model model) {
		logger.info("Home! The client locale is {}.", locale);
		
		String pageName = "home";
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		val pageData = new PageHtmlInfo();
		pageData.setName(pageName);
		pageData.setHtmlBody("Body of " + pageName);
		
		addPageToModel(pageData, model);
		
		return "view";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, params = "edit")
	private String homePageEdit(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String pageName = "home";
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		val pageData = new PageWHtmlInfo();
		pageData.setName(pageName);
		pageData.setWhtmlBody("Body of " + pageName);
		
		addPageToModel(pageData, model);
		
		return "edit";
	}
	
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String viewPage(Locale locale, Model model, @PathVariable("pageName") String pageName) {
		
		logger.info("viewPage {}, locale {}.", pageName, locale); 
		
		val pageData = new PageHtmlInfo(); 
		pageData.setName(pageName);
		pageData.setHtmlBody("Body of " + pageName);
			
		addPageToModel(pageData, model);
		
		return "view";			
	}
	
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET, params = "edit")
	public String editPage(Locale locale, Model model, @PathVariable("pageName") String pageName) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		val pageData = new PageWHtmlInfo();
		pageData.setName(pageName);
		pageData.setWhtmlBody("Body of " + pageName);
		
		addPageToModel(pageData, model);
		
		return "edit";
	}
	
}
