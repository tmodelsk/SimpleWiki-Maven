package tm.learning.simplewiki.model.services;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class WikiResult {
	
	@Getter @Setter
	private String symbol;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String description;
	

}
