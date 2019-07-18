package domain;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String keyword;
	private Category category;
	private Date minDate;
	private Date maxDate;
	private Double maxFee;
	
	private Collection<Conference> conferences;
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@ManyToOne
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMinDate() {
		return minDate;
	}
	
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMaxDate() {
		return maxDate;
	}
	
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	@Min(0)
	public Double getMaxFee() {
		return maxFee;
	}
	
	public void setMaxFee(Double maxFee) {
		this.maxFee = maxFee;
	}
	
	@ManyToMany
	public Collection<Conference> getConferences() {
		return conferences;
	}

	public void setConferences(Collection<Conference> conferences) {
		this.conferences = conferences;
	}
	
}
