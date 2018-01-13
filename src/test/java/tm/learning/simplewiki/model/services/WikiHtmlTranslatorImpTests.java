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
		
		val src = "Litwo! Ojczyzno moja! Ty jesteœ jak zdrowie. Ile ciê trzeba ceniæ, ten zamek dziœ toczy siê rzuci³ kilku m³odych od dzisiaj nie rzuca w gronie goœci nie widzia³ krótki, jasnoz³oty a w purpurowe kwiaty i przy Bernardynie, bernardyn zmówi³ krótki pacierz w kalendarzu mo¿na wydrukowaæ wszystkie zacnie zrodzone, ka¿da kochanka dziewic¹. Tadeusz, chocia¿ liczy³ lat dziesiêæ by³em dworskim Wojewody ojca Podkomorzego, Moœciwego Pana zastêpuje i inni, wiêcej godni Wojewody wzglêdów doszli potem najwy¿szych krajowych zamieszków. Dobra, ca³e zaczerwienione, jak œnieg bia³a gdzie podzia³ siê? szukaæ prawodawstwa w s³u¿bê rz¹du, by tu mieszka³? Stary ¿o³nierz, sta³ patrz¹c, dumaj¹c wonnymi powiewami kwiatów oddychaj¹c oblicze a¿ kêdy pieprz roœnie gdzie w swój rydwan or³y z³ote obok pan Wojski na siano. w pole psy zawo³ane. Teraz grzmi orê¿, a zwierzê nie ma dot¹d pierwsze zamiary odmieni³ kaza³, aby w okolicy. i k³opotach, i Asesor, razem, jakoby zlewa. I bór czerni³ siê pan Podkomorzy i stryjem, ale nigdzie nie mo¿e. Widaæ, ¿e zamczysko wziêliœmy w ulicê siê wszystkim nale¿y, lecz go pilnowa³ i zmniejsza. I zl¹k³ siê, jak drudzy i panien wiele. Stryjaszek myœli wkrótce.";
		
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
