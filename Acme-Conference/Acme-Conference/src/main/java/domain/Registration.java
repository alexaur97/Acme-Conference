package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Registration extends DomainEntity {

	private Author author;
	private Conference conference;
	private CreditCard creditCard;
	
	@NotNull
	@ManyToOne(optional=false)
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
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
	public CreditCard getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
}
