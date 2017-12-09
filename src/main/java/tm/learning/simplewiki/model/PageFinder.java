package tm.learning.simplewiki.model;

import tm.learning.simplewiki.commons.PageUri;

/** Locates Wiki & Page based on url data */
public interface PageFinder {
	
	PageAndWiki findWikiAndPage(PageUri pageUri);
}
