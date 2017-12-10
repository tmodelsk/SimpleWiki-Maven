package tm.learning.simplewiki.model.repo.base;

import tm.learning.simplewiki.model.repo.data.Page;

public interface PageDao extends EntityBaseDao<Page> {
	
	Page findPage(String wikiUrlPrefix, String pageUrlPrefix);

}
