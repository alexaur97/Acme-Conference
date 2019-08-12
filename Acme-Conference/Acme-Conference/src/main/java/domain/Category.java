
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		name;
	private String		nameEs;
	private Category	parent;
	private Boolean		root;


	@NotNull
	public Boolean getRoot() {
		return this.root;
	}

	public void setRoot(final Boolean root) {
		this.root = root;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getNameEs() {
		return this.nameEs;
	}

	public void setNameEs(final String nameEs) {
		this.nameEs = nameEs;
	}

	@OneToOne
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

}
