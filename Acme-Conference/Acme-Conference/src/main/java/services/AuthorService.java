package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Author;
import forms.ActorEditForm;
import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private ActorService actorService;

	public Author reconstructEdit(ActorEditForm actorEditForm) {
		final Author result;
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

	private Author findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Author author = this.findByUserId(user.getId());
		Assert.notNull(author);
		this.actorService.auth(author, Authority.AUTHOR);
		return author;
	}

	private Author findByUserId(int id) {
		final Author author = this.authorRepository.findByUserId(id);
		return author;
	}

	public Author save(Author author) {
		Assert.notNull(author);

		final Author result = this.authorRepository.save(author);
		return result;
		
	}
	

}
