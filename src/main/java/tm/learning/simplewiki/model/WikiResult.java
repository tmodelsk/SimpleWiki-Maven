package tm.learning.simplewiki.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class WikiResult {
	
	@Getter @Setter
	private String urlPrefix;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String description;
	

}
