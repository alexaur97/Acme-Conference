
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class PaperForm extends DomainEntity {

	private String	title;
	private String	authorAlias;
	private String	summary;
	private String	document;

	private Integer	SubmissionId;


	public Integer getSubmissionId() {
		return this.SubmissionId;
	}

	public void setSubmissionId(final Integer submissionId) {
		this.SubmissionId = submissionId;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getAuthorAlias() {
		return this.authorAlias;
	}

	public void setAuthorAlias(final String authorAlias) {
		this.authorAlias = authorAlias;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@NotBlank
	public String getDocument() {
		return this.document;
	}

	public void setDocument(final String document) {
		this.document = document;
	}

}
