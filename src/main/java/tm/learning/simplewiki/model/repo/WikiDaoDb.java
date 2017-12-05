package tm.learning.simplewiki.model.repo;

import org.hibernate.query.Query;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.val;
import tm.learning.simplewiki.model.data.Wiki;

//@Repository("wikiDao")
@Component
public class WikiDaoDb extends BaseDao<Integer, Wiki> implements WikiDao {

	@Override
	public Wiki findByUrlPrefix(String urlPrefix) {
		//val wiki = (Wiki)getEntityManager()
		
		Query<Wiki> query;
		
		if(urlPrefix != null) {
			query = sessionFactory.getCurrentSession()
					.createQuery("SELECT w FROM Wiki w WHERE w.urlPrefix = :urlPrefix", Wiki.class)			
					.setParameter("urlPrefix", urlPrefix);
		}
		else {
			query = sessionFactory.getCurrentSession()
					.createQuery("SELECT w FROM Wiki w WHERE w.urlPrefix IS NULL", Wiki.class);
		}
		
		return DataAccessUtils.singleResult(query.getResultList());
	}

	@Override
	public void save(Wiki wiki) {
		persist(wiki);
	}

}
