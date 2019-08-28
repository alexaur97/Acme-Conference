
package services;

import java.util.ArrayList;
import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	private CreditCardService		creditCardService;


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

		return result;
	}

	public Registration save(final Registration registration) {
		this.authorService.findByPrincipal();
		Assert.notNull(registration);

		return this.registrationRepository.save(registration);
	}

	public Collection<Registration> findRegistrationByAuthor(final int id) {
		Collection<Registration> res = new ArrayList<>();
		res = this.registrationRepository.findRegistrationByAuthor(id);
		return res;
	}

	public Registration constructByForm(final RegistrationForm registrationForm) {
		final Registration result = new Registration();
		final Author a = this.authorService.findByPrincipal();
		result.setAuthor(a);

		result.setConference(registrationForm.getConference());

		final CreditCard creditCard = new CreditCard();
		creditCard.setHolderName(registrationForm.getHolderName());
		creditCard.setBrandName(registrationForm.getBrandName());
		creditCard.setCvv(registrationForm.getCvv());
		creditCard.setExpirationMonth(registrationForm.getExpirationMonth());
		creditCard.setExpirationYear(registrationForm.getExpirationYear());
		creditCard.setNumber(registrationForm.getNumber());
		final Boolean b = Utils.creditCardIsExpired(creditCard);
		Assert.isTrue(!b);

		final CreditCard saved = this.creditCardService.save(creditCard);
		result.setCreditCard(saved);

		return result;

	}
	//Other Methods--------------------

	public Collection<Double> statsRegistrationsPerConference() {
		final Collection<Double> result = this.registrationRepository.statsRegistrationsPerConference();
		Assert.notNull(result);
		return result;
	}

}
