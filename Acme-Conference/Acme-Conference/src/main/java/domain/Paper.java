package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Paper extends DomainEntity{

	private String title;
	private String authorAlias;
	private String summary;
	private String document;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getAuthorAlias() {
		return authorAlias;
	}
	
	public void setAuthorAlias(String authorAlias) {
		this.authorAlias = authorAlias;
	}
	
	@NotBlank
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@NotBlank
	public String getDocument() {
		return document;
	}
	
	public void setDocument(String document) {
		this.document = document;
	}
	
}
