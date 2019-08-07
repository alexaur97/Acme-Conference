
package services;

import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.RegistrationRepository;
import domain.Author;
import domain.CreditCard;
import domain.Registration;
import forms.RegistrationForm;

@Service
@Transactional
public class RegistrationService {

	//Managed repository -------------------
	@Autowired
	private RegistrationRepository	registrationRepository;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private Validator				validator;


	//COnstructors -------------------------
	public RegistrationService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Collection<Registration> findAll() {
		Collection<Registration> result;

		result = this.registrationRepository.findAll();

		return result;
	}

	public Registration findOne(final int registrationId) {
		Registration result;

		result = this.registrationRepository.findOne(registrationId);

		return result;
	}

	public Registration create() {
		Registration result;

		result = new Registration();

		final CreditCard creditCard = new CreditCard();
		result.setCreditCard(creditCard);

		return result;
	}

	public Registration save(final Registration registration) {
		Assert.notNull(registration);

		return this.registrationRepository.save(registration);
	}

	public Registration constructByForm(final RegistrationForm registrationForm) {
		final Registration result = this.create();

		final Author a = this.authorService.findByPrincipal();

		final CreditCard creditCard = result.getCreditCard();
		creditCard.setBrandName(registrationForm.getBrandName());
		creditCard.setCvv(registrationForm.getCvv());
		creditCard.setExpirationMonth(registrationForm.getExpirationMonth());
		creditCard.setExpirationYear(registrationForm.getExpirationYear());
		creditCard.setHolderName(registrationForm.getHolderName());
		creditCard.setNumber(registrationForm.getNumber());
		final Boolean b = Utils.creditCardIsExpired(creditCard);
		Assert.isTrue(!b);
		result.setCreditCard(creditCard);
		result.setAuthor(a);

		return result;

	}
	//Other Methods--------------------

}
