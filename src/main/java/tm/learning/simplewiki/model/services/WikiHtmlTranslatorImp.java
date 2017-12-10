package tm.learning.simplewiki.model.services;

import org.springframework.stereotype.Service;

import lombok.val;
import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.repo.data.Page;

@Service
public class WikiHtmlTranslatorImp implements WikiHtmlTranslator {

	@Override
	public Html buildHtml(Page page) {
		
		val html = new Html(page.getBody());
		
		return html;
	}

	@Override
	public Html buildHtml(WikiHtml wikiHtml) {
		val html = new Html(wikiHtml.toString());
		
		return html;
	}

}
