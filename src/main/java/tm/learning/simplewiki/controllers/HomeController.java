package tm.learning.simplewiki.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.NotImplementedException;
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
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.commons.SimpleWikiBaseEx;
import tm.learning.simplewiki.model.PageMode;
import tm.learning.simplewiki.model.PageResult;
import tm.learning.simplewiki.model.WikiResult;
import tm.learning.simplewiki.model.WikiService;
import tm.learning.simplewiki.views.PageHtmlInfo;
import tm.learning.simplewiki.views.PageInfo;
import tm.learning.simplewiki.views.PageWHtmlInfo;
import tm.learning.simplewiki.views.Views;
import tm.learning.simplewiki.views.WikiInfo;

/** Handles requests for the application home page. */
@Controller
public class HomeController {
	
	// POST: /?savePage
	@RequestMapping(value = "/", method = RequestMethod.POST, params = "savePage")
	private String homePageSave(@ModelAttribute("page") PageWHtmlInfo page, BindingResult result, Locale locale, Model model) {
		logger.info("Home save! The client locale is {}.", locale);
		
		wikiService.savePage(PageUri.ROOT, page.getName(), page.getWhtmlBody());
		
		return "redirect:/";
	}

	// POST: /{pageName}?savePage
	@RequestMapping(value = "/{pageName}", method = RequestMethod.POST, params = "savePage")
	private String pageSave(@ModelAttribute("page") PageWHtmlInfo page, @PathVariable("pageName") String pageUrl, 
								BindingResult result, Locale locale, Model model) {
		logger.info("Page {} save! The client locale is {}.", pageUrl, locale);
			
		val pageUri = new PageUri(null, pageUrl);
		wikiService.savePage(pageUri, page.getName(), page.getWhtmlBody());
		
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
		
		val pageRes = wikiService.getPageResult(PageUri.ROOT, PageMode.View);
		if(pageRes == null || pageRes.wiki() == null) throw new SimpleWikiBaseEx("Wiki not found or critical error!");
		
		if(pageRes.mode() == PageMode.View) {
			addPageToViewModel(pageRes, model);
			return Views.PAGE_VIEW;
		}
		
		throw new NotImplementedException("Edit ROOT not implemented!");
	}

	// GET: /?edit
	@RequestMapping(value = "/", method = RequestMethod.GET, params = "edit")
	private String homePageEdit(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		findWikiPageFillEditModel(PageUri.ROOT, model);
		
		return Views.PAGE_EDIT;
	}
	
	// GET: /{pageName}
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String viewPage(Locale locale, Model model, @PathVariable("pageName") String pageUrl) {
		logger.info("viewPage {}, locale {}.", pageUrl, locale); 
		
		val pageUri = new PageUri(null, pageUrl);
		
		val pageRes = wikiService.getPageResult(pageUri, PageMode.View);
		if(pageRes == null || pageRes.wiki() == null) throw new SimpleWikiBaseEx("Wiki not found or critical error!");
		
		if(pageRes.mode() == PageMode.View) {
			addPageToViewModel(pageRes, model);
			return Views.PAGE_VIEW;
		} 
		else {
			addPageToEditModel(pageRes, model);
			return Views.PAGE_EDIT;
		}		
	}
	
	// GET: /{pageName}?edit
	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET, params = "edit")
	public String editPage(Locale locale, Model model, @PathVariable("pageName") String pageUrl) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		val pageUri = new PageUri(null, pageUrl);
		
		val pageRes = wikiService.getPageResult(pageUri, PageMode.View);
		if(pageRes == null || pageRes.wiki() == null) throw new SimpleWikiBaseEx("Wiki not found or critical error!");
		
		addPageToEditModel(pageRes, model);
		return Views.PAGE_EDIT;
	}

	
	private void findWikiPageFillEditModel(PageUri pageUri, Model model) {
		val pageRes = wikiService.getPageResult(PageUri.ROOT, PageMode.Edit);
		if(pageRes == null || pageRes.wiki() == null) throw new SimpleWikiBaseEx("Wiki not found or critical error!");
		
		addPageToEditModel(pageRes, model);
	}
	
	private void addPageToViewModel(PageResult pageResult, Model model ) {
		val pageData = fillPageHtml(pageResult);
		val wikiData = fillWikiInfo(pageResult.wiki());
		
		addPageToModel(pageData, wikiData, model);
	}
	private void addPageToEditModel(PageResult pageResult, Model model ) {
		val pageData = fillPageWHtml(pageResult);
		val wikiData = fillWikiInfo(pageResult.wiki());
		
		addPageToModel(pageData, wikiData, model);
	}

	private void addPageToModel(PageInfo pageData, WikiInfo wikiData, Model model) {
		model.addAttribute("page", pageData );
		model.addAttribute("wiki", wikiData );
	}
	private PageHtmlInfo fillPageHtml(PageResult page) {
		val pageData = new PageHtmlInfo();
		
		if(page != null) {
			pageData.setName(page.name());
			pageData.setHtmlBody(page.html().toString());
		}
		
		return pageData;
	}
	private PageWHtmlInfo fillPageWHtml(PageResult page) {
		val pageData = new PageWHtmlInfo();
		
		if(page != null) {
			pageData.setName(page.name());
			pageData.setWhtmlBody(page.html().toString());	
		}
		
		return pageData;
	}		
	private WikiInfo fillWikiInfo(WikiResult wiki) {
		
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki is null!");
		
		val wikiData = new WikiInfo(wiki.name() , 999);
		
		return wikiData;
	}
	
	// ### Dependencies ###
	@Autowired
	private WikiService wikiService;	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
};
