package tm.learning.simplewiki.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.val;
import tm.learning.simplewiki.model.PageResult;
import tm.learning.simplewiki.model.SimpleWikiBaseEx;
import tm.learning.simplewiki.model.WikiService;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.views.PageHtmlInfo;
import tm.learning.simplewiki.views.PageInfo;
import tm.learning.simplewiki.views.PageWHtmlInfo;
import tm.learning.simplewiki.views.Views;
import tm.learning.simplewiki.views.WikiInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	// POST: /?savePage
	@RequestMapping(value = "/", method = RequestMethod.POST, params = "savePage")
	private String homePageSave(@ModelAttribute("page") PageWHtmlInfo page, BindingResult result, Locale locale, Model model) {
		logger.info("Home save! The client locale is {}.", locale);
			
		val pageResult = wikiService.savePage(null, null, page.getName(), page.getWhtmlBody());	
		addPageToViewModel(pageResult, model);
		
		//return Views.PAGE_VIEW;
		return "redirect:/";
	}

	// POST: /{pageName}?savePage
	@RequestMapping(value = "/{pageName}", method = RequestMethod.POST, params = "savePage")
	private String pageSave(@ModelAttribute("page") PageWHtmlInfo page, @PathVariable("pageName") String pageUrl, 
								BindingResult result, Locale locale, Model model) {
		logger.info("Home save! The client locale is {}.", locale);
			
		val pageResult = wikiService.savePage(null, pageUrl, page.getName(), page.getWhtmlBody());	
		addPageToViewModel(pageResult, model);
		
		//return Views.PAGE_VIEW;
		return "redirect:/"+pageUrl;
	}	

	// GET: /
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Locale locale, Model model) {
		logger.info("Home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		//Thread.sleep(5000);
		
		findWikiPageFillViewModel(null, null, model);
		
		return "view";
	}

	// GET: /?edit
	@RequestMapping(value = "/", method = RequestMethod.GET, params = "edit")
	private String homePageEdit(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
			
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		findWikiPageFillEditModel(null, null, model);
		
		return "edit";
	}
	
	// GET: /{pageName}
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String viewPage(Locale locale, Model model, @PathVariable("pageName") String pageUrl) {
		logger.info("viewPage {}, locale {}.", pageUrl, locale); 
		
		val pageResult = getWikiAndPage(null, pageUrl);
		
		if(pageResult.page() != null) {
			addPageToViewModel(pageResult, model);
			return Views.PAGE_VIEW;
		}
		else {
		
			val wikiData = fillWikiInfo(pageResult.wiki());
			val pageData = new PageWHtmlInfo(pageUrl, "<p>Some example content of "+pageUrl+"</p>");
			
			addPageToModel(pageData, wikiData, model);

			return Views.PAGE_EDIT;
		}		
	}
	
	// GET: /{pageName}?edit
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET, params = "edit")
	public String editPage(Locale locale, Model model, @PathVariable("pageName") String pageUrl) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		findWikiPageFillEditModel(null, pageUrl, model);
				
		return "edit";
	}

	private void addPageToViewModel(PageResult pageResult, Model model ) {
		val pageData = fillPageHtml(pageResult.page());
		val wikiData = fillWikiInfo(pageResult.wiki());
		
		addPageToModel(pageData, wikiData, model);
	}
	@SuppressWarnings("unused")
	private void addPageToEditModel(PageResult pageResult, Model model ) {
		val pageData = fillPageWHtml(pageResult.page());
		val wikiData = fillWikiInfo(pageResult.wiki());
		
		addPageToModel(pageData, wikiData, model);
	}
	
	private void findWikiPageFillViewModel(String wikiUrlPrefix, String pageUrl, Model model) {
		val pageResult = getWikiAndPage(wikiUrlPrefix, pageUrl);
			
		val wikiData = fillWikiInfo(pageResult.wiki());
		val pageData = fillPageHtml(pageResult.page());
		
		addPageToModel(pageData, wikiData, model);
	}
	private void findWikiPageFillEditModel(String wikiUrlPrefix, String pageUrl, Model model) {
		val pageResult = getWikiAndPage(wikiUrlPrefix, pageUrl);
			
		val wikiData = fillWikiInfo(pageResult.wiki());
		val pageData = fillPageWHtml(pageResult.page());
		
		addPageToModel(pageData, wikiData, model);
	}
	
	private void addPageToModel(PageInfo pageData, WikiInfo wikiData, Model model) {
		model.addAttribute("page", pageData );
		model.addAttribute("wiki", wikiData );
	}

	private PageResult getWikiAndPage(String wikiUrlPrefix, String pageUrl) {
		val pageResult = wikiService.findWikiAndPage(wikiUrlPrefix, pageUrl);
		
		if(pageResult.wiki() == null) throw new SimpleWikiBaseEx("There's no default wiki!");
		
		return pageResult;
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
	
	@Autowired
	private WikiService wikiService;	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
}
