package tm.learning.simplewiki.model;

import tm.learning.simplewiki.commons.PageUri;

public interface WikiService {
	
	PageResult getPageResult(PageUri pageUri);
	
	PageResult getPageResult(PageUri pageUri, PageMode prefferedMode);
	
	PageAndWiki findWikiAndPage(String wikiUrlPrefix, String pageUrl);
	
	void savePage(PageUri pageUri, String pageName, String whtml);
	PageAndWiki savePage(String wikiUrlPrefix, String pageUrl, String pageName, String whtml);

	//void clearRepository();
	
}
