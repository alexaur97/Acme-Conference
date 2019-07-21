package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Author;
import forms.AdministratorEditForm;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private ActorService actorService;

	public Administrator findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Administrator a = this.findByUserId(user.getId());
		Assert.notNull(a);
		this.actorService.auth(a, Authority.ADMINISTRATOR);
		return a;
	}

	public Administrator findByUserId(final int id) {
		final Administrator a = this.administratorRepository.findByUserId(id);
		return a;
	}

	public AdministratorEditForm toForm(final Actor actor) {
		final AdministratorEditForm res = new AdministratorEditForm();
		res.setName(actor.getName());
		res.setSurname(actor.getSurname());
		res.setMiddleName(actor.getMiddleName());
		res.setPhoto(actor.getPhoto());
		res.setEmail(actor.getEmail());
		res.setPhone(actor.getPhone());
		res.setAddress(actor.getAddress());
		return res;
	}

	public Administrator reconstructEdit(AdministratorEditForm administratorEditForm) {
		final Administrator result;
		result = this.findByPrincipal();
		result.setName(administratorEditForm.getName());
		result.setMiddleName(administratorEditForm.getMiddleName());
		result.setSurname(administratorEditForm.getSurname());
		result.setPhoto(administratorEditForm.getPhoto());
		result.setEmail(administratorEditForm.getEmail());
		result.setPhone(this.actorService.addCountryCode(administratorEditForm.getPhone()));
		result.setAddress(administratorEditForm.getAddress());
		Assert.notNull(result);
		return result;
	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);

		final Administrator result = this.administratorRepository.save(administrator);
		return result;
		
	}
}
