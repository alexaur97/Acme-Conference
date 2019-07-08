package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends DomainEntity {

	private String title;
	private String acronym;
	private String venue;
	private Date submission;
	private Date notification;
	private Date cameraReady;
	private Date startDate;
	private Date endDate;
	private String summary;
	private Double fee;
	
	private Administrator administrator;
	private Category category;

	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getAcronym() {
		return acronym;
	}
	
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	@NotBlank
	public String getVenue() {
		return venue;
	}
	
	
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	@NotNull
	public Date getSubmission() {
		return submission;
	}
	
	public void setSubmission(Date submission) {
		this.submission = submission;
	}
	
	@NotNull
	public Date getNotification() {
		return notification;
	}
	
	public void setNotification(Date notification) {
		this.notification = notification;
	}
	
	@NotNull
	public Date getCameraReady() {
		return cameraReady;
	}
	
	public void setCameraReady(Date cameraReady) {
		this.cameraReady = cameraReady;
	}
	
	@NotNull
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@NotNull
	@Future
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@NotBlank
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@NotNull
	public Double getFee() {
		return fee;
	}
	
	public void setFee(Double fee) {
		this.fee = fee;
	}

	@NotNull
	@ManyToOne(optional=false)
	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
