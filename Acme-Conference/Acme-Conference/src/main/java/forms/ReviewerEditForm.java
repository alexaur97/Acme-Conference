package forms;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class ReviewerEditForm {

	private String name;
	private String middleName;
	private String surname;
	private String email;
	private String photo;
	private String phone;
	private String address;
	private Collection<String> keyWords;

	public ReviewerEditForm() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Column(unique = true)
	@Pattern(regexp = "([a-zA-Z0-9])+@([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)+|[a-zA-Z0-9]+[ a-zA-Z0-9]*\\<([a-zA-Z0-9])+@([a-zA-Z0-9]+\\.[a-zA-Z0-9]+)+\\>")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Collection<String> keyWords) {
		this.keyWords = keyWords;
	}
}
