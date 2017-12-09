package tm.learning.simplewiki.commons;

/** Base helper class to create 'Typed' String based classes */
public abstract class StringAdapter {
	
	private final String value;
	
	public StringAdapter(String value) {
		super();
		this.value = value;
	}


	@Override
	public String toString() {
		return value;
	}

}
