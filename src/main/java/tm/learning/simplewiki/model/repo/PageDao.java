package tm.learning.simplewiki.model.repo;

import tm.learning.simplewiki.model.data.Page;

public interface PageDao extends EntityBaseDao<Page> {
	
	Page findPage(String wikiUrlPrefix, String pageUrlPrefix);

}
