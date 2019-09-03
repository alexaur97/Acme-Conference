
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
public class Report extends DomainEntity {

	private Reviewer			reviewer;
	private Submission			submission;

	private Integer				originality;
	private Integer				quality;
	private Integer				readability;
	private String				decision;
	private Collection<String>	comments;


	@NotNull
	@ManyToOne(optional = false)
	public Reviewer getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(final Reviewer reviewer) {
		this.reviewer = reviewer;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Integer getOriginality() {
		return this.originality;
	}

	public void setOriginality(final Integer originality) {
		this.originality = originality;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Integer getQuality() {
		return this.quality;
	}

	public void setQuality(final Integer quality) {
		this.quality = quality;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Integer getReadability() {
		return this.readability;
	}

	public void setReadability(final Integer readability) {
		this.readability = readability;
	}

	@NotBlank
	@Pattern(regexp = "^(REJECT|BORDER-LINE|ACCEPT)$")
	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

}
