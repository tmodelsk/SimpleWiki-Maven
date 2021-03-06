package tm.learning.simplewiki.model.services;

import java.time.LocalDateTime;

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
import tm.learning.simplewiki.model.repo.base.PageDao;
import tm.learning.simplewiki.model.repo.base.WikiDao;
import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.repo.data.Wiki;

/** Wiki Service - implementation */
@Service
@Transactional
public class WikiServiceImp implements WikiService {

	@Override
	public PageResult getPageResult(PageUri pageUri) {
		ensureInitialized();
		
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
			
			res.updatedDate(page.getUpdatedDate());
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
	public void savePage(PageUri pageUri, String pageName, String whtml) {
		
		val srcResp = pageFinderServ.findWikiAndPage(pageUri);
		val wiki = srcResp.wiki();
		if(wiki == null) 
			throw new SimpleWikiBaseEx(Ctm.msgFormat("Wiki '{0}' not found!", pageUri.wikiPrefix() != null ? pageUri.wikiPrefix() : "ROOT"));
		Page page = srcResp.page();
		
		if(page == null) {
			// it's new page
			page = new Page();
			page.setSymbol(pageUri.pagePrefix());
			page.setName(pageName);
			page.setBody(whtml);
			
			wiki.addPage(page);
			
			wikiDao.save(wiki);
		}
		else {
			page.setName(pageName);
			page.setBody(whtml);
			page.setUpdatedDate(LocalDateTime.now());
			
			pageDao.save(page);
		}		
	}

	
	public void ensureInitialized() {		
		if(!initializeOnCreation) return;
		
		if(wikiDao == null) return;
		
		if(!initializationDone && !initializating) {
			try {
				initializating = true;
				logger.info("Initializing default wiki data");
				
				val wiki = new Wiki();
				wiki.setSymbol(null);
				wiki.setName("Main wiki");
				wiki.setDescription("Default main wiki");
				
				val page = new Page();
				page.setSymbol(null);
				page.setName("Main");
				
				val strB = new StringBuilder();
				val nl = System.lineSeparator();
				strB.append("<h3>Simple Java based wiki</h3>"+nl);
				strB.append("New wiki page: [[newPage]]" + nl);
				strB.append("<b>Source code:</b> <a href='https://github.com/tmodelsk/SimpleWiki-Maven' target=_blank>github.com/tmodelsk/SimpleWiki-Maven</a>" + nl);
				strB.append("<b>Stack: </b>");
				strB.append("Java 8, Maven, Spring MVC 5, Hibernate 5 + HSQLDB" + nl);
				strB.append("<b>Deployed: </b>");
				strB.append("Azure website, Tomcat 8.5" + nl);
				page.setBody(strB.toString());
				page.setDefault(true);
				
				wiki.addPage(page);
							
				wikiDao.save(wiki);
				//pageDao.save(page);
				initializationDone = true;	
			}
			finally {
				initializating = false;
			}
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
	private static boolean initializationDone = false;
	private static boolean initializating = false;

	private static final Logger logger = LoggerFactory.getLogger(WikiServiceImp.class);
}
