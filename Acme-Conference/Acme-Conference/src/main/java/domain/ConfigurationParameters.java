
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String				systemName;
	private String				banner;
	private String				message;
	private String				messageEs;
	private String				countryCode;
	private Collection<String>	creditCardMakes;
	private Collection<String>	voidWords;
	private Collection<String>	voidWordsEs;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@NotBlank
	public String getMessageEs() {
		return this.messageEs;
	}

	public void setMessageEs(final String messageEs) {
		this.messageEs = messageEs;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getVoidWords() {
		return this.voidWords;
	}

	public void setVoidWords(final Collection<String> voidWords) {
		this.voidWords = voidWords;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getVoidWordsEs() {
		return this.voidWordsEs;
	}

	public void setVoidWordsEs(final Collection<String> voidWordsEs) {
		this.voidWordsEs = voidWordsEs;
	}

}
