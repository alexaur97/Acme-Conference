package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import domain.Conference;

public class MakeSubmissionForm {

	
	private Conference conference;
	private String title;
	private String authorAlias;
	private String summary;
	private String document;
	
	public MakeSubmissionForm() {
		super();
	}

	@NotNull
	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

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
