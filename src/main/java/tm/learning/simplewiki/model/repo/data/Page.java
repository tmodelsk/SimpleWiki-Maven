package tm.learning.simplewiki.model.repo.data;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="page")
public class Page {
	
	@Getter @Setter
	@Id @GeneratedValue
	@Column(name = "Id", unique=true, nullable=false)
	private Long id;
	
	@Getter @Setter
	@ManyToOne @JoinColumn(name="WikiId")
	private Wiki wiki;
	
	@Getter @Setter
	@Basic
	private String symbol;
	
	@Getter @Setter
	@Basic
	private String name;
	
	@Getter @Setter
	private boolean isDefault;
	
	@Getter @Setter
	@Basic
	private String body;
	
	@Getter @Setter
	private LocalDateTime createdDate;

	@Getter @Setter
	private LocalDateTime updatedDate;
		
	public Page(String name, String symbol, String body) {
		this();
		this.name = name;
		this.symbol = symbol;
		this.body = body;
	}

	public Page() {
		super();
		
		createdDate = LocalDateTime.now();
		updatedDate = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isDefault ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((wiki == null) ? 0 : wiki.hashCode());
		
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDefault != other.isDefault)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (wiki == null) {
			if (other.wiki != null)
				return false;
		} else if (!wiki.equals(other.wiki))
			return false;
		return true;
	}
	
	
}
