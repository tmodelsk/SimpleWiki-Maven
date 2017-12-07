package tm.learning.simplewiki.model.repo;

import tm.learning.simplewiki.model.data.Wiki;

public interface WikiDao extends EntityBaseDao<Wiki> {
	
	Wiki findByUrlPrefix(String urlPrefix);

}
