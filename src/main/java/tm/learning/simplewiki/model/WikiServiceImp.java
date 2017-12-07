package tm.learning.simplewiki.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.val;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.model.repo.PageDao;
import tm.learning.simplewiki.model.repo.WikiDao;

/** Wiki Service - implementation */
@Service
@Transactional
public class WikiServiceImp implements WikiService {

	@Override
	public PageResult getWikiAndPage(String wikiUrlPrefix, String pageName) {
		ensureInitialized();
		
		val wiki = getWiki(wikiUrlPrefix);
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
		
		val page = getPage(wiki, pageName);
		
		return new PageResult(wiki, page);
	}
		
	@Override
	public PageResult savePage(String wikiUrlPrefix, String pageUrl, String pageName, String whtml) {
		ensureInitialized();
		
		val wiki = getWiki(wikiUrlPrefix);
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
		
		Page page = getPage(wiki, pageUrl);
		if(page == null) {
			// it's new page
			page = new Page();
			page.setUrlPrefix(pageUrl);
			page.setName(pageName);
			page.setBody(whtml);
			
			wiki.getPages().add(page);
		}
		else {
			page.setName(pageName);
			page.setBody(whtml);	
		}
		//throw new SimpleWikiBaseEx("Page not found");
				
		return new PageResult(wiki, page);
	}
	
	private Page getPage(Wiki wiki, String pageName) {		
		return pageDao.findPage(wiki.getUrlPrefix(), pageName);
	}
	
	private Wiki getWiki(String wikiUrlPrefix) {		
		return wikiDao.findByUrlPrefix(wikiUrlPrefix);
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
			
			wiki.getPages().add(page);
						
			wikiDao.save(wiki);
			initializationDone = true;
		}
	}
	
	//@Override
	public void clearRepository() {
		//wikies.clear();
	}
		
	@Autowired
	@Qualifier("WikiDaoMem")
	private WikiDao wikiDao;
	
	@Autowired
	@Qualifier("PageDaoMem")
	private PageDao pageDao;
	
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
