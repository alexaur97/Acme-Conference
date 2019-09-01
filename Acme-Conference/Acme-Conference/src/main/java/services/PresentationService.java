
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PresentationRepository;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationRepository	presentationRepository;

	@Autowired
	private Validator				validator;


	//COnstructors -------------------------
	public PresentationService() {
		super();
	}

	public Presentation create() {
		Presentation result;

		result = new Presentation();

		return result;
	}

	public Collection<Presentation> findAll() {
		Collection<Presentation> result;

		result = this.presentationRepository.findAll();

		return result;
	}

	public Presentation findOne(final int presentationId) {
		Presentation result;

		result = this.presentationRepository.findOne(presentationId);

		return result;
	}

	public Presentation save(final Presentation presentation) {
		Assert.notNull(presentation);

		return this.presentationRepository.save(presentation);
	}

	public void delete(final Presentation presentation) {
		this.presentationRepository.delete(presentation);
	}

	public Collection<Presentation> findByConference(final int id) {
		Collection<Presentation> res = new ArrayList<>();
		res = this.presentationRepository.findByConference(id);
		return res;
	}

	public Presentation reconstruct(final Presentation presentation, final BindingResult binding) {
		final Presentation res = presentation;
		this.validator.validate(res, binding);
		return res;
	}
}
