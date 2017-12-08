package tm.learning.simplewiki.model;

public interface WikiService {
	
	PageResult findWikiAndPage(String wikiUrlPrefix, String pageUrl);
	
	PageResult savePage(String wikiUrlPrefix, String pageUrl, String pageName, String whtml);

	//void clearRepository();
	
}
