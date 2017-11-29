package tm.learning.simplewiki.model;

public interface WikiService {
	
	PageResult getWikiAndPage(String wikiUrlPrefix, String pageName);

}
