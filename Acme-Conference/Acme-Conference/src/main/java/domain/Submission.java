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
	private Conference conference;
	
	private String ticker;
	private Date moment;
	private Paper paper;
	private Paper cameraReady;
	private String status;
	
	@NotNull
	@ManyToOne(optional=false)
	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

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
	
	@NotNull
	@ManyToOne(optional=false)
	public Paper getPaper() {
		return paper;
	}
	
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	
	@ManyToOne
	public Paper getCameraReady() {
		return cameraReady;
	}
	
	public void setCameraReady(Paper cameraReady) {
		this.cameraReady = cameraReady;
	}
	
	@NotBlank
	@Pattern(regexp="^(UNDER-REVIEW|REJECTED|ACCEPTED)$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
