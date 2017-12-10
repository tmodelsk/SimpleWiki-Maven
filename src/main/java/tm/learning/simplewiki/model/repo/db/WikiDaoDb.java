package tm.learning.simplewiki.model.repo.db;

import org.hibernate.query.Query;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import tm.learning.simplewiki.model.repo.base.BaseDao;
import tm.learning.simplewiki.model.repo.base.WikiDao;
import tm.learning.simplewiki.model.repo.data.Wiki;

@Repository("WikiDaoDb")
public class WikiDaoDb extends BaseDao<Integer, Wiki> implements WikiDao {

	@Override
	public Wiki findByUrlPrefix(String urlPrefix) {
		//val wiki = (Wiki)getEntityManager()
		
		Query<Wiki> query;
		
		if(urlPrefix != null) {
			query = session()
					.createQuery("SELECT w FROM Wiki w WHERE w.urlPrefix = :urlPrefix", Wiki.class)			
					.setParameter("urlPrefix", urlPrefix);
		}
		else {
			query = session()
					.createQuery("SELECT w FROM Wiki w WHERE w.urlPrefix IS NULL", Wiki.class);
		}
		
		return DataAccessUtils.singleResult(query.getResultList());
	}

	@Override
	public void save(Wiki wiki) {
		persist(wiki);
	}

}
