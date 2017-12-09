package tm.learning.simplewiki.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tm.learning.simplewiki.commons.PageUri;

@Accessors(fluent=true)
public class PageResult {
	
	@Getter @Setter 
	private PageUri pageUri= null;
	
	@Getter @Setter
	private String name= null;
	
	@Getter @Setter
	private Html html = null;
	
	@Getter @Setter
	private WikiHtml wikiHtml= null;
	
	@Getter @Setter
	private WikiResult wiki= null;

	@Getter @Setter
	private PageMode mode;	
}
