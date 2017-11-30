package tm.learning.simplewiki.model.data;

import lombok.Getter;
import lombok.Setter;

public class Page {
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private boolean isDefault;
	
	@Getter @Setter
	private String urlPrefix;
	
	@Getter @Setter
	private String body;

		
	public Page(String name, String urlPrefix, String body) {
		super();
		this.name = name;
		this.urlPrefix = urlPrefix;
		this.body = body;
	}

	public Page() {
		super();
	}
}
