package tm.learning.simplewiki.model.repo.mem;

import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.val;
import tm.learning.simplewiki.model.SimpleWikiBaseEx;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.repo.PageDao;

@Repository("PageDaoMem")
public class PageDaoMem implements PageDao  {

	@Override
	public void save(Page entity) {
		throw new NotImplementedException("save()");
	}

	@Override
	public Page findPage(String wikiUrlPrefix, String pageUrlPrefix) {
		val wiki = wikiDao.findByUrlPrefix(wikiUrlPrefix);
		
		if(wiki == null) throw new SimpleWikiBaseEx("Wiki not found!");
		
		Page page = null;
		Optional<Page> pageOpt;
		if(pageUrlPrefix == null) pageOpt = wiki.getPages().stream().filter(p -> p.isDefault()).findFirst();
		else pageOpt = wiki.getPages().stream().filter(p -> p.getUrlPrefix() != null && p.getUrlPrefix().equals(pageUrlPrefix)).findFirst();
		
		if(pageOpt.isPresent()) page = pageOpt.get();
		
		return page;
	}
	
	@Autowired
	private WikiDaoMem wikiDao;

}
