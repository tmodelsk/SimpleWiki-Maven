package tm.learning.simplewiki.model.services;

import tm.learning.simplewiki.commons.PageUri;

public interface WikiService {
	
	PageResult getPageResult(PageUri pageUri);
	
	PageResult getPageResult(PageUri pageUri, PageMode prefferedMode);
	
	void savePage(PageUri pageUri, String pageName, String whtml);
}
