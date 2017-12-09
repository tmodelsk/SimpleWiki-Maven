package tm.learning.simplewiki.model;

import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.data.Page;

/** Creates page plain Html basing on page Wiki-Html */
public interface WikiHtmlTranslator {
	
	Html buildHtml(Page page);
	
	Html buildHtml(WikiHtml wikiHtml);

}
