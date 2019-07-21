package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Author;
import domain.Sponsor;
import forms.ActorEditForm;
import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class SponsorService {
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	@Autowired
	private ActorService actorService;

	public Sponsor reconstructEdit(ActorEditForm actorEditForm) {
		final Sponsor result;
		result = this.findByPrincipal();
		result.setName(actorEditForm.getName());
		result.setMiddleName(actorEditForm.getMiddleName());
		result.setSurname(actorEditForm.getSurname());
		result.setPhoto(actorEditForm.getPhoto());
		result.setEmail(actorEditForm.getEmail());
		result.setPhone(this.actorService.addCountryCode(actorEditForm.getPhone()));
		result.setAddress(actorEditForm.getAddress());
		Assert.notNull(result);
		return result;
	}

	private Sponsor findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Sponsor sponsor = this.findByUserId(user.getId());
		Assert.notNull(sponsor);
		this.actorService.auth(sponsor, Authority.SPONSOR);
		return sponsor;
	}

	private Sponsor findByUserId(int id) {
		final Sponsor sponsor = this.sponsorRepository.findByUserId(id);
		return sponsor;
	}

	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);

		final Sponsor result = this.sponsorRepository.save(sponsor);
		return result;
		
	}
	
	

}
