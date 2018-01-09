package tm.learning.simplewiki.model.repo.base;

import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.repo.data.Wiki;

public interface PageDao extends EntityBaseDao<Page> {
	
	Page findPage(String wikiUrlPrefix, String pageUrlPrefix);
	
	Page findPage(Wiki wiki, String pageUrlPrefix);

}
