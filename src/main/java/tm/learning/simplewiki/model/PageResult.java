package tm.learning.simplewiki.model;

import tm.learning.simplewiki.model.data.Page;
import tm.learning.simplewiki.model.data.Wiki;

public class PageResult {
	
	private Wiki wiki;
	public Wiki wiki() {
		return wiki;
	}
	
	private Page page;
	public Page page() {
		return page;
	}

	
	
	public PageResult(Wiki wiki, Page page) {
		super();
		this.wiki = wiki;
		this.page = page;
	}

	public PageResult() {
		super();
	}

}
