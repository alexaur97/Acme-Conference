
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Reviewer;
import domain.Submission;
import forms.RegisterReviewerForm;
import forms.ReviewerEditForm;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository		reviewerRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	public ReviewerEditForm toForm(final Reviewer reviewer) {
		final ReviewerEditForm result = new ReviewerEditForm();
		result.setName(reviewer.getName());
		result.setSurname(reviewer.getSurname());
		result.setMiddleName(reviewer.getMiddleName());
		result.setPhoto(reviewer.getPhoto());
		result.setEmail(reviewer.getEmail());
		result.setPhone(this.actorService.addCountryCode(reviewer.getPhone()));
		result.setAddress(reviewer.getAddress());
		result.setKeyWords(reviewer.getKeyWords());
		return result;
	}

	public Reviewer findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Reviewer a = this.findByUserId(user.getId());
		Assert.notNull(a);
		this.actorService.auth(a, Authority.REVIEWER);
		return a;
	}

	public Reviewer findByUserId(final int id) {
		final Reviewer a = this.reviewerRepository.findByUserId(id);
		return a;
	}

	public Reviewer reconstruct(final RegisterReviewerForm registerForm) {
		final Reviewer res = new Reviewer();

		Assert.isTrue(registerForm.getPassword().equals(registerForm.getRepeatPassword()), "register.password.error");
		final UserAccount userAccount = new UserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass = encoder.encodePassword(registerForm.getPassword(), null);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REVIEWER);
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
		res.setKeyWords(registerForm.getKeyWords());

		Assert.isTrue(registerForm.getTerms() == true, "assertCheck");
		final Collection<String> emails = this.actorService.findAllEmails();
		final String email = registerForm.getEmail();
		final boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail, "register.username.error");
		return res;
	}

	public Reviewer reconstructEdit(final ReviewerEditForm reviewerEditForm) {
		final Reviewer result;
		result = this.findByPrincipal();
		final String lastEmail = result.getEmail();
		final Collection<String> emails = this.actorService.findAllEmails();

		result.setName(reviewerEditForm.getName());
		result.setMiddleName(reviewerEditForm.getMiddleName());
		result.setSurname(reviewerEditForm.getSurname());
		result.setPhoto(reviewerEditForm.getPhoto());
		result.setEmail(reviewerEditForm.getEmail());
		result.setPhone(this.actorService.addCountryCode(reviewerEditForm.getPhone()));
		result.setAddress(reviewerEditForm.getAddress());
		result.setKeyWords(reviewerEditForm.getKeyWords());
		Assert.notNull(result);

		emails.remove(lastEmail);
		final String email = reviewerEditForm.getEmail();
		final boolean bEmail = !emails.contains(email);
		Assert.isTrue(bEmail, "edition.email.error");

		return result;
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);

		final Reviewer result = this.reviewerRepository.save(reviewer);
		return result;

	}
	public Collection<Reviewer> findAll() {
		this.administratorService.findByPrincipal();
		final Collection<Reviewer> res = this.reviewerRepository.findAll();
		return res;
	}

	public Collection<Reviewer> findWithoutSubmission() {
		final Collection<Reviewer> res = this.reviewerRepository.findWithoutSubmission();
		return res;
	}

	public Collection<Reviewer> findBySubmission(final Submission s) {
		final Collection<Reviewer> res = this.reviewerRepository.findBySubmissionID(s.getId());
		return res;
	}

	public void refuseSubmission() {
		final Reviewer reviewer = this.findByPrincipal();
		reviewer.setSubmission(null);
		this.save(reviewer);
	}

}
