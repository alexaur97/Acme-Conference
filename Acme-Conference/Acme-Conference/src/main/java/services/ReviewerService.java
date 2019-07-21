package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Author;
import domain.Reviewer;
import forms.ReviewerEditForm;
import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ReviewerService {
	
	@Autowired
	private ReviewerRepository reviewerRepository;
	
	@Autowired
	private ActorService actorService;

	public ReviewerEditForm toForm(Reviewer reviewer) {
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

	public Reviewer reconstructEdit(ReviewerEditForm reviewerEditForm) {
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

	public Reviewer save(Reviewer reviewer) {
		Assert.notNull(reviewer);

		final Reviewer result = this.reviewerRepository.save(reviewer);
		return result;
		
	}

	
}
