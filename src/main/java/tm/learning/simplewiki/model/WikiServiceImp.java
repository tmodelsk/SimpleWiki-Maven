package tm.learning.simplewiki.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.val;
import tm.common.Ctm;
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.commons.SimpleWikiBaseEx;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.model.repo.PageDao;
import tm.learning.simplewiki.model.repo.WikiDao;

/** Wiki Service - implementation */
@Service
@Transactional
public class WikiServiceImp implements WikiService {

	@Override
	public PageResult getPageResult(PageUri pageUri) {
		val srcResp = pageFinderServ.findWikiAndPage(pageUri);
		val wiki = srcResp.wiki();
		if(wiki == null) 
			throw new SimpleWikiBaseEx(Ctm.msgFormat("Wiki '{0}' not found!", pageUri.wikiPrefix() != null ? pageUri.wikiPrefix() : "ROOT"));
		val page = srcResp.page();
		
		val res = new PageResult();
		
		res.pageUri(pageUri);
		
		if(page != null) {	// page is found!
			res.mode(PageMode.View);
			res.name(page.getName());
			
			val html = wikiHtmlTranslator.buildHtml(page);
			res.html(html);
			res.wikiHtml( new WikiHtml(page.getBody()));
		}
		else {
			res.mode(PageMode.Edit);
			res.name(pageUri.pagePrefix());
			
			res.wikiHtml(new WikiHtml(Ctm.msgFormat("Example content of '{0}' page", res.name())));
			val html = wikiHtmlTranslator.buildHtml(res.wikiHtml());
			res.html(html);
		}
		
		val wikiRes = new WikiResult();
		wikiRes.name(wiki.getName());
		wikiRes.description(wiki.getDescription());
		
		res.wiki(wikiRes);
		
		return res;
	}
	
	@Override
	public PageResult getPageResult(PageUri pageUri, PageMode prefferedMode) {
		val resultDefault = getPageResult(pageUri);
		
		if(resultDefault.mode() == prefferedMode) return resultDefault;
		
		if(prefferedMode == PageMode.Edit) {
			
			resultDefault.mode(prefferedMode);
			return resultDefault;
		}
		
		return resultDefault;
	}
	
	@Override
	public PageAndWiki findWikiAndPage(String wikiUrlPrefix, String pageName) {
		ensureInitialized();
			
		val page = pageDao.findPage(wikiUrlPrefix, pageName);
		
		Wiki wiki;
		if(page != null) wiki = page.getWiki();
		else wiki = wikiDao.findByUrlPrefix(wikiUrlPrefix);
		
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
		
		return new PageAndWiki(wiki, page);
	}
		
	@Override
	public PageAndWiki savePage(String wikiUrlPrefix, String pageUrl, String pageName, String whtml) {
		ensureInitialized();
		
		Wiki wiki=null;
		
		Page page = pageDao.findPage(wikiUrlPrefix, pageUrl);
		if(page == null) {
			// it's new page
			page = new Page();
			page.setUrlPrefix(pageUrl);
			page.setName(pageName);
			page.setBody(whtml);
			
			wiki = wikiDao.findByUrlPrefix(wikiUrlPrefix);
			if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
			wiki.addPage(page);
		}
		else {
			page.setName(pageName);
			page.setBody(whtml);
			
			wiki = page.getWiki();
		}
		//throw new SimpleWikiBaseEx("Page not found");
				
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
		
		wikiDao.save(wiki);
		pageDao.save(page);
		
		return new PageAndWiki(wiki, page);
	}
	
	public void ensureInitialized() {		
		if(!initializeOnCreation) return;
		
		if(!initializationDone) {
			logger.info("Initializing default wiki data");
			
			val wiki = new Wiki();
			wiki.setUrlPrefix(null);
			wiki.setName("Main wiki");
			wiki.setDescription("Default main wiki");
			
			val page = new Page();
			page.setUrlPrefix(null);
			page.setName("Main");
			page.setBody("<h3>Some body</h3>");
			page.setDefault(true);
			
			wiki.addPage(page);
						
			wikiDao.save(wiki);
			//pageDao.save(page);
			initializationDone = true;
		}
	}
	
	//@Override 
	public void clearRepository() {
		//wikies.clear();
	}
		
	@Autowired
	//@Qualifier("WikiDaoMem")
	@Qualifier("WikiDaoDb")
	private WikiDao wikiDao;
	
	@Autowired
	//@Qualifier("PageDaoMem")
	@Qualifier("PageDaoDb")
	private PageDao pageDao;
	
	@Autowired
	private PageFinder pageFinderServ;
	@Autowired
	private WikiHtmlTranslator wikiHtmlTranslator;
	
	
	public WikiServiceImp(boolean initialize) {
		super();
		initializeOnCreation = initialize;
		//if(initialize) ensureInitialized();
	}
	public WikiServiceImp() {
		this(true);
	}

	private boolean initializeOnCreation = false;
	private boolean initializationDone = false;

	private static final Logger logger = LoggerFactory.getLogger(WikiServiceImp.class);
}
