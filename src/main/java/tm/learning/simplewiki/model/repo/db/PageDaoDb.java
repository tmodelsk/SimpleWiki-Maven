package tm.learning.simplewiki.model.repo.db;

import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import lombok.val;
import tm.learning.simplewiki.model.repo.base.BaseDao;
import tm.learning.simplewiki.model.repo.base.PageDao;
import tm.learning.simplewiki.model.repo.data.Page;

@Repository("PageDaoDb")
public class PageDaoDb extends BaseDao<Integer, Page> implements PageDao {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Page findPage(String wikiUrlPrefix, String pageUrlPrefix) {
		
		val sess = session();
		
		val pageCr = sess.createCriteria(Page.class);
		
		if(pageUrlPrefix != null) pageCr.add(Restrictions.eq("urlPrefix", pageUrlPrefix));
		else pageCr.add(Restrictions.isNull("urlPrefix"));
		
		
		val wikiCr = pageCr.createCriteria("wiki");
		
		if(wikiUrlPrefix != null) wikiCr.add(Restrictions.eq("urlPrefix", wikiUrlPrefix));
		else wikiCr.add(Restrictions.isNull("urlPrefix"));
		
		//pageCr.list();
		
		return (Page) DataAccessUtils.singleResult(pageCr.list());
	}

}
