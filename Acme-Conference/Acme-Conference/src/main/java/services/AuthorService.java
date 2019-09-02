
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Author;
import domain.Finder;
import forms.ActorEditForm;
import forms.RegisterSponsorAndAuthorForm;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private AuthorRepository	authorRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FinderService		finderService;


	public Author reconstruct(final RegisterSponsorAndAuthorForm registerForm) {
		final Author res = new Author();

		Assert.isTrue(registerForm.getPassword().equals(registerForm.getRepeatPassword()), "register.password.error");
		final UserAccount userAccount = new UserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass = encoder.encodePassword(registerForm.getPassword(), null);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUTHOR);
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

	public void assignFinderToAuthor(final int authorId) {
		final Finder f = new Finder();
		f.setAuthor(this.findOne(authorId));
		this.finderService.saveForRegister(f);
	}

	public Author reconstructEdit(final ActorEditForm actorEditForm) {
		final Author result;
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

	public Author findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Author author = this.findByUserId(user.getId());
		Assert.notNull(author);
		this.actorService.auth(author, Authority.AUTHOR);
		return author;
	}

	public Author findByUserId(final int id) {
		final Author author = this.authorRepository.findByUserId(id);
		return author;
	}

	public Collection<Author> findAll() {
		final Collection<Author> authors = this.authorRepository.findAll();
		return authors;
	}

	public Author findOne(final int authorId) {
		final Author author = this.authorRepository.findOne(authorId);
		return author;
	}

	public Author save(final Author author) {
		Assert.notNull(author);

		final Author result = this.authorRepository.save(author);
		return result;

	}

	public Collection<Author> getAuthorsRegisterInConference(final int conferenceId) {
		final Collection<Author> result = this.authorRepository.getAuthorsRegisterInConference(conferenceId);
		return result;
	}

	public Collection<Author> getAuthorsSubmittedToConference(final int conferenceId) {
		final Collection<Author> result = this.authorRepository.getAuthorsSubmittedToConference(conferenceId);
		return result;
	}

}
