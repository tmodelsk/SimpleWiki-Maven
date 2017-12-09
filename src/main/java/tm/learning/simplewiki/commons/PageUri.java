package tm.learning.simplewiki.commons;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class PageUri {
	
	@Getter
	private final String wikiPrefix;
	
	@Getter
	private final String pagePrefix;
	
	public static final PageUri ROOT = new PageUri(null, null);

	public PageUri(String wikiPrefix, String pagePrefix) {
		super();
		this.wikiPrefix = wikiPrefix;
		this.pagePrefix = pagePrefix;
	}
}
