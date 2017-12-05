package tm.learning.simplewiki.model.repo;

import tm.learning.simplewiki.model.data.Wiki;

public interface WikiDao {
	
	Wiki findByUrlPrefix(String urlPrefix);
	
	void save(Wiki wiki);

}
