
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private Collection<String>	keyWords;

	private Submission			submission;


	@ElementCollection(fetch=FetchType.EAGER)
	@NotEmpty
	public Collection<String> getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(final Collection<String> keyWords) {
		this.keyWords = keyWords;
	}

	@OneToOne(optional = true)
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

}
