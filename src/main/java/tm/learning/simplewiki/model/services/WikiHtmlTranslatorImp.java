package tm.learning.simplewiki.model.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.val;
import tm.common.Ctm;
import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.repo.data.Page;

@Service
public class WikiHtmlTranslatorImp implements WikiHtmlTranslator {

	@Override
	public Html buildHtml(Page page) {
		
		// wikidot : [[[Archers & Crossbowmen availability]]]
		
		val htmlStr = processWikiHtml(page.getBody());
		val html = new Html(htmlStr);
		
		return html;
	}

	@Override
	public Html buildHtml(WikiHtml wikiHtml) {
		val htmlStr = processWikiHtml(wikiHtml.toString());
		val html = new Html(htmlStr);
		
		return html;
	}
	
	private String processWikiHtml(String whtml) {
		String res = "";
		
		Matcher matcher = pagePattern.matcher(whtml);
		
		while(matcher.find()) {
			String prefix, linkStr, suffix;
			
			prefix = matcher.group(1);
			linkStr = matcher.group(2);
			suffix = matcher.group(3);
			
			whtml = prefix;
			whtml += Ctm.msgFormat("<a href='{0}'>{1}</a>", linkStr, linkStr);
			whtml += suffix;
			
			matcher = pagePattern.matcher(whtml);
		}
		
		res = whtml;
		
		return res;
	}

	
	private static final Pattern pagePattern = Pattern.compile("(.*)\\[\\[(.+)\\]\\](.*)");
}
