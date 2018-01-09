package tm.learning.simplewiki.model.services;

import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.repo.data.Wiki;

/** Creates page plain Html basing on page Wiki-Html */
public interface WikiHtmlTranslator {
	
	Html buildHtml(Page page);
	
	Html buildHtml(WikiHtml wikiHtml);
	Html buildHtml(WikiHtml wikiHtml, Wiki wiki);

}
