package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity {

	private String name;
	private String nameEs;
	
	@NotBlank
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getNameEs() {
		return nameEs;
	}
	
	public void setNameEs(String nameEs) {
		this.nameEs = nameEs;
	}
	
	
	
}
