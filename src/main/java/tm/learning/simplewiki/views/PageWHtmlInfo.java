package tm.learning.simplewiki.views;

import lombok.Getter;
import lombok.Setter;

public class PageWHtmlInfo extends PageInfo {

	/** Wiki-Html body */
	@Getter @Setter
	private String whtmlBody;
	
	public PageWHtmlInfo(String name, String whtmlBody) {
		super(name);
		this.whtmlBody = whtmlBody;
	}

	public PageWHtmlInfo() {
		super();
	}

	private static final long serialVersionUID = -3799539649242087518L;
}
