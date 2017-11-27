package tm.learning.simplewiki.views;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public abstract class PageInfo implements Serializable {
	
	@Getter @Setter
	private String name;

	public PageInfo() {
		super();
	}
	
	private static final long serialVersionUID = 9116134336019391134L;
}
