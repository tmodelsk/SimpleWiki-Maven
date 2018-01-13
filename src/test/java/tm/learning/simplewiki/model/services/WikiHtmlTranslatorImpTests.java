package tm.learning.simplewiki.model.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lombok.val;
import tm.common.Ctm;
import tm.learning.simplewiki.commons.WikiHtml;
import tm.learning.simplewiki.model.repo.base.PageDao;
import tm.learning.simplewiki.model.repo.data.Page;
import tm.learning.simplewiki.model.repo.data.Wiki;

public class WikiHtmlTranslatorImpTests {
	
	@Test
	public void singleWikiLinkWithoutTitle_toExistingPage_Should_linkInnerTextEqualToPageName() {
		
		val wiki = new Wiki();
		
		val targetPage = new Page();
		targetPage.setSymbol("targetPage");
		targetPage.setName("SomeTargetPageName");
		wiki.addPage(targetPage);
		
		val thisPage = new Page("thisPage", "thisPage", Ctm.msgFormat("[[{0}]]", targetPage.getSymbol()));
		wiki.addPage(thisPage);
		
		when(pageDao.findPage(wiki, targetPage.getSymbol())).thenReturn(targetPage);
		
		val html = wikiTranslator.buildHtml(thisPage);
		
		assertThat(html).isNotNull();
		val htmlStr = html.toString();
		val linkInnerHtml = getInnerHtml(htmlStr);
		assertThat(linkInnerHtml).isEqualTo(targetPage.getName());
	}
	
	@Test
	public void singleWikiLink_ShouldTranslate() {
		val serv = new WikiHtmlTranslatorImp();
		
		val whtml = "[[aa]]";
		
		val html = serv.buildHtml(new WikiHtml(whtml));
		
		assertThat(html).isNotNull();
		
		val htmlStr = html.toString();
		assertThat(htmlStr).isNotEqualTo(whtml);
		assertThat(htmlStr).contains("<a ");
		assertThat(htmlStr).contains("</a>");
		
		val innerText = getInnerHtml(htmlStr);
		assertThat(innerText).isEqualTo("aa");
	}

	@Test
	public void newLine_shouldAddBr() {
		val serv = new WikiHtmlTranslatorImp();
		
		val src = "aaa"+System.lineSeparator()+"bbb";
		
		val html = serv.buildHtml(new WikiHtml(src));
		assertThat(html).isNotNull();
		val htmlStr = html.toString();
		assertThat(htmlStr).startsWith("aaa");
		assertThat(htmlStr).endsWith("bbb");
		assertThat(htmlStr).contains("<br/>");
	}
	
	@Test
	public void noWikiText_ShouldBeSame() {
		val serv = new WikiHtmlTranslatorImp();
		
		val src = "Litwo! Ojczyzno moja! Ty jeste� jak zdrowie. Ile ci� trzeba ceni�, ten zamek dzi� toczy si� rzuci� kilku m�odych od dzisiaj nie rzuca w gronie go�ci nie widzia� kr�tki, jasnoz�oty a w purpurowe kwiaty i przy Bernardynie, bernardyn zm�wi� kr�tki pacierz w kalendarzu mo�na wydrukowa� wszystkie zacnie zrodzone, ka�da kochanka dziewic�. Tadeusz, chocia� liczy� lat dziesi�� by�em dworskim Wojewody ojca Podkomorzego, Mo�ciwego Pana zast�puje i inni, wi�cej godni Wojewody wzgl�d�w doszli potem najwy�szych krajowych zamieszk�w. Dobra, ca�e zaczerwienione, jak �nieg bia�a gdzie podzia� si�? szuka� prawodawstwa w s�u�b� rz�du, by tu mieszka�? Stary �o�nierz, sta� patrz�c, dumaj�c wonnymi powiewami kwiat�w oddychaj�c oblicze a� k�dy pieprz ro�nie gdzie w sw�j rydwan or�y z�ote obok pan Wojski na siano. w pole psy zawo�ane. Teraz grzmi or�, a zwierz� nie ma dot�d pierwsze zamiary odmieni� kaza�, aby w okolicy. i k�opotach, i Asesor, razem, jakoby zlewa. I b�r czerni� si� pan Podkomorzy i stryjem, ale nigdzie nie mo�e. Wida�, �e zamczysko wzi�li�my w ulic� si� wszystkim nale�y, lecz go pilnowa� i zmniejsza. I zl�k� si�, jak drudzy i panien wiele. Stryjaszek my�li wkr�tce.";
		
		val html = serv.buildHtml(new WikiHtml(src));
		assertThat(html).isNotNull();
		val htmlStr = html.toString();
		
		assertThat(htmlStr).isEqualTo(src);
	}
	
	@Test
	public void emptyText_ShouldBeSame() {
		val serv = new WikiHtmlTranslatorImp();
		
		val src = "";
		
		val html = serv.buildHtml(new WikiHtml(src));
		assertThat(html).isNotNull();
		val htmlStr = html.toString();
		
		assertThat(htmlStr).isEqualTo(src);
	}
	
	@Test
	public void nullText_ShouldBeNull() {
		val serv = new WikiHtmlTranslatorImp();
		
		String src = null;
		
		val html = serv.buildHtml(new WikiHtml(src));
		assertThat(html).isNotNull();
		
		val htmlStr = html.toString();
		assertThat(htmlStr).isNull();

	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	private WikiHtmlTranslatorImp wikiTranslator;
	@Mock
	private PageDao pageDao;
	
	private String getInnerHtml(String htmlStartEndTagStr) {
		val m = innerHtmlPattern.matcher(htmlStartEndTagStr);
		
		if(m.find()) {
			return m.group(3);
		} else return null;
	}
	private static final Pattern innerHtmlPattern = Pattern.compile("<([^<> ]*)([^<>]*)?>([^>]*)<\\/([^<>]*)>");
}
