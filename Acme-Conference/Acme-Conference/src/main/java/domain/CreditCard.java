package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

	private String holderName;
	private String brandName;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private String cvv;
	
	@NotBlank
	public String getHolderName() {
		return holderName;
	}
	
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	
	@NotBlank
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	@NotBlank
	public String getNumber() {
		return number;
	}
	
	@CreditCardNumber
	@Pattern(regexp = "^\\d+$")
	public void setNumber(String number) {
		this.number = number;
	}
	
	@NotNull
	@Range(min=1, max=12)
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@NotNull
	@Range(min=19, max=99)
	public Integer getExpirationYear() {
		return expirationYear;
	}
	
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@NotBlank
	@Length(min=3, max=3)
	@Pattern(regexp = "^\\d+$")
	public String getCvv() {
		return cvv;
	}
	
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
