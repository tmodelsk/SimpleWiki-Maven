package tm.learning.simplewiki.model.repo.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="wiki")
public class Wiki {

	@Getter @Setter
	@Id
	@GeneratedValue
	@Column(name = "Id", unique=true, nullable=false)
	private Long id;
	
	@Getter @Setter
	@Column(unique=true)
	private String symbol;

	@Getter @Setter
	private String name;

	@Getter @Setter
	private String description;

	@Getter @Setter
	@OneToMany(mappedBy="wiki", cascade=CascadeType.ALL)
	private List<Page> pages = new ArrayList<>();
	
	public void addPage(Page page) {
		pages.add(page);
		page.setWiki(this);
	}

	public Wiki(String name, String description, String symbol) {
		this();

		this.name = name;
		this.description = description;
		this.symbol = symbol;
	}

	public Wiki() {
		super();

		pages = new ArrayList<>();
	}
}
