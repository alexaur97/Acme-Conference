package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private Collection<String> keyWords;

	@ElementCollection
	@NotEmpty
	public Collection<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Collection<String> keyWords) {
		this.keyWords = keyWords;
	}
	
	
	
}
