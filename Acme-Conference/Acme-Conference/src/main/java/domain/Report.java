package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity{
	
	private Reviewer reviewer;
	private Submission submission;
	
	private Integer originality;
	private Integer quality;
	private Integer readability;
	private String decision;
	private Collection<String> comments;
	
	@NotNull
	@ManyToOne(optional=false)
	public Reviewer getReviewer() {
		return reviewer;
	}
	
	public void setReviewer(Reviewer reviewer) {
		this.reviewer = reviewer;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	public Submission getSubmission() {
		return submission;
	}
	
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	
	@NotNull
	@Range(min=0, max=10)
	public Integer getOriginality() {
		return originality;
	}
	
	public void setOriginality(Integer originality) {
		this.originality = originality;
	}
	
	@NotNull
	@Range(min=0, max=10)
	public Integer getQuality() {
		return quality;
	}
	
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	
	@NotNull
	@Range(min=0, max=10)
	public Integer getReadability() {
		return readability;
	}
	
	public void setReadability(Integer readability) {
		this.readability = readability;
	}
	
	@NotBlank
	@Pattern(regexp="^(REJECT|BORDER-LINE|ACCEPT)$")
	public String getDecision() {
		return decision;
	}
	
	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	@ElementCollection
	@NotEmpty
	public Collection<String> getComments() {
		return comments;
	}
	
	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}
	
	
	
}
