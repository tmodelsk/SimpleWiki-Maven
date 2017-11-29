package tm.learning.simplewiki.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class Wiki {

	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String description;
	
	@Getter @Setter
	private String urlPrefix;

	@Getter @Setter
	private List<Page> pages;
	
	
	public Wiki(String name, String description, String urlPrefix) {
		this();
		
		this.name = name;
		this.description = description;
		this.urlPrefix = urlPrefix;
	}


	public Wiki() {
		super();
		
		pages = new ArrayList<>();
	}	
}
