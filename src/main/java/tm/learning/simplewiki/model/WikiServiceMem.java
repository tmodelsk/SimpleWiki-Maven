package tm.learning.simplewiki.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.val;
import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;

/** Wiki Service - in memory implementation */
@Service
public class WikiServiceMem implements WikiService {

	@Override
	public PageResult getWikiAndPage(String wikiUrlPrefix, String pageName) {
		ensureInitialized();
		
		Wiki wiki = null;
		if(wikiUrlPrefix == null) {
			val wikiOpt = wikies.stream().filter(w -> w.getUrlPrefix() == null).findFirst();
			if(wikiOpt.isPresent()) wiki = wikiOpt.get();
		} else {
			val wikiOpt = wikies.stream().filter(w -> w.getUrlPrefix().equals(wikiUrlPrefix)).findFirst();
			if(wikiOpt.isPresent()) wiki = wikiOpt.get();
		}
		
		if(wiki == null) throw new RuntimeException();
		
		Page page = null;
		Optional<Page> pageOpt;
		if(pageName == null) pageOpt = wiki.getPages().stream().filter(p -> p.getUrlPrefix() == null).findFirst();
		else pageOpt = wiki.getPages().stream().filter(p -> p.getUrlPrefix().equals(pageName)).findFirst();
		
		if(pageOpt.isPresent()) page = pageOpt.get();
		
		return new PageResult(wiki, page);
	}
	
	
	private void ensureInitialized() {
		if(wikies != null && wikies.size() > 0) return;
		
		val wiki = new Wiki();
		wiki.setUrlPrefix(null);
		wiki.setName("Main wiki");
		wiki.setDescription("Default main wiki");
		
		val page = new Page();
		page.setUrlPrefix(null);
		page.setName("Main");
		page.setBody("<h3>Some body</h3>");
		
		wiki.getPages().add(page);
		
		wikies = new ArrayList<>();
		wikies.add(wiki);
	}
	
	
	private static List<Wiki> wikies; // = new ArrayList<>();

}
