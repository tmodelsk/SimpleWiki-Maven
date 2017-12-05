package tm.learning.simplewiki.model.data;

import java.util.ArrayList;
import java.util.List;

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

	@Id
	@GeneratedValue
	@Column(name = "Id", unique=true, nullable=false)
	private Long id;

	@Getter @Setter
	private String name;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private String urlPrefix;

	@Getter @Setter
	@OneToMany(mappedBy="wiki")
	private List<Page> pages = new ArrayList<>();

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
