package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String systemName;
	private String banner;
	private String message;
	private String messageEs;
	private String countryCode;
	private Collection<String> creditCardMakes;
	private Collection<String> voidWords;
	private Collection<String> voidWordsEs;
	
	@NotBlank
	public String getSystemName() {
		return systemName;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@NotBlank
	@URL
	public String getBanner() {
		return banner;
	}
	
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	@NotBlank
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@NotBlank
	public String getMessageEs() {
		return messageEs;
	}
	
	public void setMessageEs(String messageEs) {
		this.messageEs = messageEs;
	}
	
	@NotBlank
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@ElementCollection
	@NotEmpty
	public Collection<String> getCreditCardMakes() {
		return creditCardMakes;
	}
	
	public void setCreditCardMakes(Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getVoidWords() {
		return voidWords;
	}

	public void setVoidWords(Collection<String> voidWords) {
		this.voidWords = voidWords;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getVoidWordsEs() {
		return voidWordsEs;
	}

	public void setVoidWordsEs(Collection<String> voidWordsEs) {
		this.voidWordsEs = voidWordsEs;
	}
	
}
