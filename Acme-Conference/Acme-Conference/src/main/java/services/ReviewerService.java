
package services;

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
import forms.RegisterReviewerForm;
import forms.ReviewerEditForm;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository	reviewerRepository;

	@Autowired
	private ActorService		actorService;


	public ReviewerEditForm toForm(final Reviewer reviewer) {
		final ReviewerEditForm result = new ReviewerEditForm();
		result.setName(reviewer.getName());
		result.setSurname(reviewer.getSurname());
		result.setMiddleName(reviewer.getMiddleName());
		result.setPhoto(reviewer.getPhoto());
		result.setEmail(reviewer.getEmail());
		result.setPhone(reviewer.getPhone());
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
		res.setPhone(registerForm.getPhone());
		res.setMiddleName(registerForm.getMiddleName());
		res.setSurname(registerForm.getSurname());
		res.setKeyWords(registerForm.getKeyWords());

		Assert.isTrue(registerForm.getTerms() == true, "assertCheck");

		return res;
	}

	public Reviewer reconstructEdit(final ReviewerEditForm reviewerEditForm) {
		final Reviewer result;
		result = this.findByPrincipal();
		result.setName(reviewerEditForm.getName());
		result.setMiddleName(reviewerEditForm.getMiddleName());
		result.setSurname(reviewerEditForm.getSurname());
		result.setPhoto(reviewerEditForm.getPhoto());
		result.setEmail(reviewerEditForm.getEmail());
		result.setPhone(this.actorService.addCountryCode(reviewerEditForm.getPhone()));
		result.setAddress(reviewerEditForm.getAddress());
		result.setKeyWords(reviewerEditForm.getKeyWords());
		Assert.notNull(result);
		return result;
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);

		final Reviewer result = this.reviewerRepository.save(reviewer);
		return result;

	}

}
