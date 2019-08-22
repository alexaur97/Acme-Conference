
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.ConfigurationParameters;
import forms.ActorEditForm;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository					actorRepository;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	public Boolean auth(final Actor a, final String auth) {
		final UserAccount userAccount = a.getUserAccount();
		final Collection<Authority> allAuths = userAccount.getAuthorities();
		final Authority au = new Authority();
		au.setAuthority(auth);
		final Boolean res = allAuths.contains(au);
		Assert.isTrue(res);
		return res;
	}

	public Actor findByPrincipal() {
		UserAccount userAccount;
		Actor result;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;

		result = this.actorRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public Boolean authEdit(final Actor a, final String auth) {
		final UserAccount userAccount = a.getUserAccount();
		final Collection<Authority> allAuths = userAccount.getAuthorities();
		final Authority au = new Authority();
		au.setAuthority(auth);
		final Boolean res = allAuths.contains(au);
		return res;
	}

	public ActorEditForm toForm(final Actor actor) {
		final ActorEditForm result = new ActorEditForm();
		result.setName(actor.getName());
		result.setSurname(actor.getSurname());
		result.setMiddleName(actor.getMiddleName());
		result.setPhoto(actor.getPhoto());
		result.setEmail(actor.getEmail());
		result.setPhone(actor.getPhone());
		result.setAddress(actor.getAddress());
		return result;
	}

	public String addCountryCode(String phoneNumber) {
		if (phoneNumber.length() > 0 && phoneNumber.charAt(0) != '+') {
			final ConfigurationParameters cp = this.configurationParametersService.find();
			final String cc = cp.getCountryCode();
			phoneNumber = cc + " " + phoneNumber;
		}
		return phoneNumber;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<String> findAllEmails() {
		final Collection<String> result = this.actorRepository.findAllEmails();
		return result;
	}

}
