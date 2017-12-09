package tm.learning.simplewiki.model;

import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;

public class PageAndWiki {
	
	private Wiki wiki = null;
	public Wiki wiki() {
		return wiki;
	}
	
	private Page page = null;
	public Page page() {
		return page;
	}

	
	
	public PageAndWiki(Wiki wiki, Page page) {
		super();
		this.wiki = wiki;
		this.page = page;
	}

	public PageAndWiki() {
		super();
	}

}
