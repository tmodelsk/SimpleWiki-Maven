package tm.learning.simplewiki.model.repo.mem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import lombok.val;
import tm.learning.simplewiki.model.repo.base.WikiDao;
import tm.learning.simplewiki.model.repo.data.Wiki;

@Repository("WikiDaoMem")
public class WikiDaoMem implements WikiDao {

	@Override
	public Wiki findByUrlPrefix(String urlPrefix) {
		Wiki wiki = null;
		
		Stream<Wiki> query;
		if(urlPrefix != null) 
			query = wikies.stream().filter( w -> w.getSymbol() != null && w.getSymbol().equals(urlPrefix));
		else
			query = wikies.stream().filter( w -> w.getSymbol() == null );
		
		val resultOpt = query.findFirst();
		
		if(resultOpt.isPresent())
			wiki= resultOpt.get();
		
		return wiki;
	}

	@Override
	public void save(Wiki wiki) {
		
		wikies.add(wiki);
	}

	
	private static List<Wiki> wikies = new ArrayList<>();
}
