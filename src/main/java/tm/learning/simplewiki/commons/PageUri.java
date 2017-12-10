package tm.learning.simplewiki.commons;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class PageUri {
	
	@Getter
	private final String wikiPrefix;
	
	@Getter
	private final String pagePrefix;
	

	public PageUri(String wikiPrefix, String pagePrefix) {
		super();
		this.wikiPrefix = wikiPrefix;
		this.pagePrefix = pagePrefix;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pagePrefix == null) ? 0 : pagePrefix.hashCode());
		result = prime * result + ((wikiPrefix == null) ? 0 : wikiPrefix.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageUri other = (PageUri) obj;
		if (pagePrefix == null) {
			if (other.pagePrefix != null)
				return false;
		} else if (!pagePrefix.equals(other.pagePrefix))
			return false;
		if (wikiPrefix == null) {
			if (other.wikiPrefix != null)
				return false;
		} else if (!wikiPrefix.equals(other.wikiPrefix))
			return false;
		return true;
	}

	public static final PageUri ROOT = new PageUri(null, null);
}
