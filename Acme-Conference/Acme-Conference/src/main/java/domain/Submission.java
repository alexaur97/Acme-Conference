package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {
	
	private Author author;
	
	private String ticker;
	private Date moment;
	private Paper submissionPaper;
	private Paper cameraReady;
	private String status;

	@NotNull
	@ManyToOne(optional=false)
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@NotBlank
	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotNull
	public Date getMoment() {
		return moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@OneToOne(optional=false)
	public Paper getSubmissionPaper() {
		return submissionPaper;
	}
	
	public void setSubmissionPaper(Paper submissionPaper) {
		this.submissionPaper = submissionPaper;
	}
	
	@OneToOne
	public Paper getCameraReady() {
		return cameraReady;
	}
	
	public void setCameraReady(Paper cameraReady) {
		this.cameraReady = cameraReady;
	}
	
	@NotBlank
	@Pattern(regexp="^(UNDER-REVIEW|REJETED|ACCEPTED)$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
