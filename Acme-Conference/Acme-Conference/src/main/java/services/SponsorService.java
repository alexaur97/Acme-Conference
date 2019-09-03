
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Conference;
import domain.Sponsor;
import domain.Sponsorship;
import forms.ActorEditForm;
import forms.RegisterSponsorAndAuthorForm;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository	sponsorRepository;

	@Autowired
	private ActorService		actorService;


	public Sponsor reconstruct(final RegisterSponsorAndAuthorForm registerForm) {
		final Sponsor res = new Sponsor();

		Assert.isTrue(registerForm.getPassword().equals(registerForm.getRepeatPassword()), "register.password.error");
		Assert.isTrue(registerForm.getPassword().equals(registerForm.getRepeatPassword()), "register.password.error");
		final UserAccount userAccount = new UserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass = encoder.encodePassword(registerForm.getPassword(), null);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		userAccount.addAuthority(authority);
		userAccount.setUsername(registerForm.getUsername());
		userAccount.setPassword(pass);

		res.setUserAccount(userAccount);
		res.setAddress(registerForm.getAddress());
		res.setPhoto(registerForm.getPhoto());
		res.setEmail(registerForm.getEmail());
		res.setName(registerForm.getName());
		res.setPhone(this.actorService.addCountryCode(registerForm.getPhone()));
		res.setMiddleName(registerForm.getMiddleName());
		res.setSurname(registerForm.getSurname());

		Assert.isTrue(registerForm.getTerms() == true, "assertCheck");

		final Collection<String> emails = this.actorService.findAllEmails();
		final String email = registerForm.getEmail();
		final boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail, "register.username.error");

		return res;
	}

	public Sponsor reconstructEdit(final ActorEditForm actorEditForm) {
		final Sponsor result;
		result = this.findByPrincipal();
		final String lastEmail = result.getEmail();
		final Collection<String> emails = this.actorService.findAllEmails();

		result.setName(actorEditForm.getName());
		result.setMiddleName(actorEditForm.getMiddleName());
		result.setSurname(actorEditForm.getSurname());
		result.setPhoto(actorEditForm.getPhoto());
		result.setEmail(actorEditForm.getEmail());
		result.setPhone(this.actorService.addCountryCode(actorEditForm.getPhone()));
		result.setAddress(actorEditForm.getAddress());
		Assert.notNull(result);

		emails.remove(lastEmail);
		final String email = actorEditForm.getEmail();
		final boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail, "edition.email.error");

		return result;
	}

	public Sponsor findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Sponsor sponsor = this.findByUserId(user.getId());
		Assert.notNull(sponsor);
		this.actorService.auth(sponsor, Authority.SPONSOR);
		return sponsor;
	}

	private Sponsor findByUserId(final int id) {
		final Sponsor sponsor = this.sponsorRepository.findByUserId(id);
		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		final Sponsor result = this.sponsorRepository.save(sponsor);
		return result;

	}



}
