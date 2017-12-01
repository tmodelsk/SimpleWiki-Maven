package tm.learning.simplewiki.model;

public interface WikiService {
	
	PageResult getWikiAndPage(String wikiUrlPrefix, String pageUrl);
	
	PageResult savePage(String wikiUrlPrefix, String pageUrl, String pageName, String whtml);

	//void clearRepository();
	
}
