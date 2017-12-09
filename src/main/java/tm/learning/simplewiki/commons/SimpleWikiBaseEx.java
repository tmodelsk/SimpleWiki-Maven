package tm.learning.simplewiki.commons;

public class SimpleWikiBaseEx extends RuntimeException {

	public SimpleWikiBaseEx() {
		super();
	}

	public SimpleWikiBaseEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SimpleWikiBaseEx(String message, Throwable cause) {
		super(message, cause);
	}

	public SimpleWikiBaseEx(String message) {
		super(message);
	}

	public SimpleWikiBaseEx(Throwable cause) {
		super(cause);
	}
	
	private static final long serialVersionUID = -8838941244124906903L;
}
