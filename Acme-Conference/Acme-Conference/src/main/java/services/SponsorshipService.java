
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Conference;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Service
@Transactional
public class SponsorshipService {

	//Managed repository -------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private Validator				validator;


	//Supporting Services ------------------

	//COnstructors -------------------------
	public SponsorshipService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Sponsorship create() {
		Sponsorship result;

		result = new Sponsorship();

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);

		return result;
	}

	public void save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		this.sponsorshipRepository.save(sponsorship);
	}

	public void delete(final Sponsorship sponsorship) {
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
		this.sponsorshipRepository.delete(sponsorship);
	}
	//Other Methods--------------------

	public Collection<Sponsorship> findBySponsor(final Integer sponsorId) {
		return this.sponsorshipRepository.findSponshorshipsBySponsor(sponsorId);
	}
	public Sponsorship recostruction(final SponsorshipForm sponsorshipForm) {
		final Sponsorship sponsorship = new Sponsorship();
		sponsorship.setBanner(sponsorshipForm.getBanner());
		sponsorship.setConference(sponsorshipForm.getConference());
		sponsorship.setSponsor(this.sponsorService.findByPrincipal());
		sponsorship.setTargetUrl(sponsorshipForm.getTargetUrl());
		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName(sponsorshipForm.getBrandName());
		creditCard.setCvv(sponsorshipForm.getCvv());
		creditCard.setExpirationMonth(sponsorshipForm.getExpirationMonth());
		creditCard.setExpirationYear(sponsorshipForm.getExpirationYear());
		creditCard.setHolderName(sponsorshipForm.getHolderName());
		creditCard.setNumber(sponsorshipForm.getNumber());
		creditCard = this.creditCardService.save(creditCard);
		sponsorship.setCreditCard(creditCard);
		return sponsorship;
	}
	public Sponsorship recostructionEdit(final Sponsorship sponsorshipFinal, final BindingResult binding) {
		final Sponsorship sponsorship = this.findOne(sponsorshipFinal.getId());
		sponsorshipFinal.setCreditCard(sponsorship.getCreditCard());
		sponsorshipFinal.setSponsor(sponsorship.getSponsor());
		this.validator.validate(sponsorshipFinal, binding);
		return sponsorshipFinal;
	}

		public Sponsorship findRandomByConference(int conferenceId) {
		Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findSponshorshipsByConference(conferenceId);
		Sponsorship result;
		if(sponsorships.isEmpty()) {
			result = null;
		}else {
		int i = (int) (Math.random() * sponsorships.size());
		result = (Sponsorship) sponsorships.toArray()[i];
		}
		return result;
	}
}
