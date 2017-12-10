package tm.learning.simplewiki.model.repo.base;

import tm.learning.simplewiki.model.repo.data.Wiki;

public interface WikiDao extends EntityBaseDao<Wiki> {
	
	Wiki findByUrlPrefix(String urlPrefix);

}
