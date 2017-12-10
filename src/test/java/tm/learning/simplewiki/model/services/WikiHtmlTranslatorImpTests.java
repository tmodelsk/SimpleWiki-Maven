package tm.learning.simplewiki.model.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import tm.learning.simplewiki.commons.WikiHtml;

public class WikiHtmlTranslatorImpTests {
	
	@Test
	public void singleWikiLink_ShouldTranslate() {
		val serv = new WikiHtmlTranslatorImp();
		
		val whtml = "[[aa]]";
		
		val html = serv.buildHtml(new WikiHtml(whtml));
		
		assertThat(html).isNotNull();
		
		val htmlStr = html.toString();
		assertThat(htmlStr).isNotEqualTo(whtml);
		
	}

}
