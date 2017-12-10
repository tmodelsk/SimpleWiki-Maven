package tm.learning.simplewiki.views;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class PageHtmlInfo extends PageInfo {

	@Getter @Setter
	private String htmlBody;
	
	@Getter @Setter
	private LocalDateTime updatedDate;
	
	private static final long serialVersionUID = 206008043088123857L;

}
