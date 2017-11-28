package tm.learning.simplewiki.views;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class WikiInfo implements Serializable {
	
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private int pagesCount;
	
	
	
	

	public WikiInfo(String name, int pagesCount) {
		super();
		this.name = name;
		this.pagesCount = pagesCount;
	}




	public WikiInfo() {
		super();
	}




	private static final long serialVersionUID = 3665993681814979927L;	
}
