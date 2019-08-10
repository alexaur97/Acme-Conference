
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TutorialRepository;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;

	@Autowired
	private Validator			validator;


	//COnstructors -------------------------
	public TutorialService() {
		super();
	}

	public Tutorial create() {
		Tutorial result;

		result = new Tutorial();

		return result;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();

		return result;
	}

	public Tutorial findOne(final int tutorialId) {
		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);

		return result;
	}

	public void save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		this.tutorialRepository.save(tutorial);
	}

	public void delete(final Tutorial tutorial) {
		this.tutorialRepository.delete(tutorial);
	}

	public Collection<Tutorial> findByConference(final int id) {
		Collection<Tutorial> res = new ArrayList<>();
		res = this.tutorialRepository.findByConference(id);
		return res;
	}

	public Tutorial reconstruct(final Tutorial tutorial, final BindingResult binding) {
		final Tutorial res = tutorial;
		this.validator.validate(res, binding);
		return res;
	}
}
