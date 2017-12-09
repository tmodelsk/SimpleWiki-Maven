package tm.learning.simplewiki.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.val;
import tm.learning.simplewiki.commons.PageUri;
import tm.learning.simplewiki.model.data.Wiki;
import tm.learning.simplewiki.model.repo.PageDao;
import tm.learning.simplewiki.model.repo.WikiDao;

@Service
public class PageFinderImp implements PageFinder {

	@Override
	public PageAndWiki findWikiAndPage(PageUri pageUri) {
		val page = pageDao.findPage(pageUri.wikiPrefix(), pageUri.pagePrefix());
		
		Wiki wiki;
		if(page != null) wiki = page.getWiki();
		else wiki = wikiDao.findByUrlPrefix(pageUri.wikiPrefix());
		
		return new PageAndWiki(wiki, page);
	}
	
	@Autowired
	//@Qualifier("WikiDaoMem")
	@Qualifier("WikiDaoDb")
	private WikiDao wikiDao;
	
	@Autowired
	//@Qualifier("PageDaoMem")
	@Qualifier("PageDaoDb")
	private PageDao pageDao;

}
