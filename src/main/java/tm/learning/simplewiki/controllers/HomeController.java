package tm.learning.simplewiki.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.val;
import tm.learning.simplewiki.model.SimpleWikiBaseEx;
import tm.learning.simplewiki.model.WikiService;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.views.PageHtmlInfo;
import tm.learning.simplewiki.views.PageInfo;
import tm.learning.simplewiki.views.PageWHtmlInfo;
import tm.learning.simplewiki.views.WikiInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private void addPageToModel(PageInfo pageData, WikiInfo wikiData, Model model) {
		model.addAttribute("page", pageData );
		model.addAttribute("wiki", wikiData );
	}
	
	private PageHtmlInfo fillPageHtml(Page page) {
		val pageData = new PageHtmlInfo();
		
		if(page != null) {
			pageData.setName(page.getName());
			pageData.setHtmlBody(page.getBody());	
		}
		
		return pageData;
	}
	private PageWHtmlInfo fillPageWHtml(Page page) {
		val pageData = new PageWHtmlInfo();
		
		if(page != null) {
			pageData.setName(page.getName());
			pageData.setWhtmlBody(page.getBody());	
		}
		
		return pageData;
	}
	private WikiInfo fillWikiInfo(Wiki wiki) {
		
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki is null!");
		
		val wikiData = new WikiInfo(wiki.getName() , 999);
		
		return wikiData;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Locale locale, Model model) {
		logger.info("Home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//Thread.sleep(5000);
		
		val pageResult = wikiService.getWikiAndPage(null, null);
		
		if(pageResult.wiki() == null) throw new SimpleWikiBaseEx("There's no default wiki!");
		
		val wikiData = fillWikiInfo(pageResult.wiki());
		val pageData = fillPageHtml(pageResult.page());
		
		addPageToModel(pageData, wikiData, model);
		
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
		
		val pageResult = wikiService.getWikiAndPage(null, null);
		
		val pageData = fillPageWHtml(pageResult.page());
		val wikiData = fillWikiInfo(pageResult.wiki());
		
		addPageToModel(pageData, wikiData, model);
		
		return "edit";
	}
	
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String viewPage(Locale locale, Model model, @PathVariable("pageName") String pageName) {
		
		logger.info("viewPage {}, locale {}.", pageName, locale); 
		
		val pageData = new PageHtmlInfo(); 
		pageData.setName(pageName);
		pageData.setHtmlBody("Body of " + pageName);
			
		val wikiData = new WikiInfo("some wiki" , 5);
		addPageToModel(pageData, wikiData,model);
		
		return "view";			
	}
	
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET, params = "edit")
	public String editPage(Locale locale, Model model, @PathVariable("pageName") String pageName) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		val pageData = new PageWHtmlInfo();
		pageData.setName(pageName);
		pageData.setWhtmlBody("Body of " + pageName);
		
		val wikiData = new WikiInfo("some wiki" , 5);
		addPageToModel(pageData, wikiData, model);
		
		return "edit";
	}

	@Autowired
	private WikiService wikiService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
}