package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity{

	private String banner;
	private String targetUrl;
	private CreditCard creditCard;
	
	private ConferenceSponsor conferenceSponsor;
	private Conference conference;
	
	@NotBlank
	@URL
	public String getBanner() {
		return banner;
	}
	
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	@NotBlank
	@URL
	public String getTargetUrl() {
		return targetUrl;
	}
	
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	@NotNull
	@OneToOne(optional=false)
	public CreditCard getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	public ConferenceSponsor getConferenceSponsor() {
		return conferenceSponsor;
	}
	
	public void setConferenceSponsor(ConferenceSponsor conferenceSponsor) {
		this.conferenceSponsor = conferenceSponsor;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	public Conference getConference() {
		return conference;
	}
	
	public void setConference(Conference conference) {
		this.conference = conference;
	}
	
}	

