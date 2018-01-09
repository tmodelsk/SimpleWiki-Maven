package tm.learning.simplewiki.model.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.val;
import tm.common.Ctm;
import tm.learning.simplewiki.commons.Html;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.repo.base.PageDao;
import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.repo.data.Wiki;

@Service
public class WikiHtmlTranslatorImp implements WikiHtmlTranslator {

	@Override
	public Html buildHtml(Page page) {
		
		// wikidot : [[[Archers & Crossbowmen availability]]]
		
		val wikiHtml = new WikiHtml(page.getBody());
		val html = buildHtml(wikiHtml, page.getWiki());
		
		return html;
	}

	@Override
	public Html buildHtml(WikiHtml wikiHtml) {
		return buildHtml(wikiHtml, null);
	}
	
	@Override
	public Html buildHtml(WikiHtml wikiHtml, Wiki wiki) {
		val htmlSourceStr = wikiHtml.toString();
		
		if(htmlSourceStr == null) return new Html(null);
		if(htmlSourceStr.isEmpty()) return new Html("");
		
		String resStr = "";
		StringBuilder strB;
		
		strB = new StringBuilder(htmlSourceStr);
		Matcher matcher = pageLinkPattern.matcher(strB);
		
		while(matcher.find()) {
			String prefix, linkStr, suffix, linkUrl=null, linkName=null;
			
			prefix = matcher.group(1);
			linkStr = matcher.group(2);
			
			boolean targetFounded = false;
			if(wiki != null) {
				val targetPage = pageDao.findPage(wiki, linkStr);
				if(targetPage != null) {
					targetFounded = true;
					linkUrl = targetPage.getUrlPrefix();
					linkName = targetPage.getName();
				}
			}
			if(!targetFounded) linkUrl = linkName = linkStr;
			
			suffix = matcher.group(3);
			
			strB = new StringBuilder();
			strB.append(prefix);
			strB.append(Ctm.msgFormat("<a href='{0}'>{1}</a>", linkUrl, linkName));
			strB.append(suffix);
			
			matcher = pageLinkPattern.matcher(strB);
		}
		
		resStr = strB.toString();
		resStr = resStr.replaceAll(System.lineSeparator(), "<br/>");
		
		
		val resultHtml = new Html(resStr);
		return resultHtml;
	}


	private static final Pattern pageLinkPattern = Pattern.compile("(.*)\\[\\[(.+)\\]\\](.*)", Pattern.DOTALL | Pattern.MULTILINE);
	
	@Autowired
	//@Qualifier("PageDaoMem")
	@Qualifier("PageDaoDb")
	private PageDao pageDao;
}
