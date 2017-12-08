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
	public PageResult findWikiAndPage(String wikiUrlPrefix, String pageName) {
		ensureInitialized();
			
		val page = pageDao.findPage(wikiUrlPrefix, pageName);
		
		Wiki wiki;
		if(page != null) wiki = page.getWiki();
		else wiki = wikiDao.findByUrlPrefix(wikiUrlPrefix);
		
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
		
		return new PageResult(wiki, page);
	}
		
	@Override
	public PageResult savePage(String wikiUrlPrefix, String pageUrl, String pageName, String whtml) {
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
		
		return new PageResult(wiki, page);
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
