
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SectionRepository;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private Validator			validator;


	//COnstructors -------------------------
	public SectionService() {
		super();
	}

	public Section create() {
		Section result;

		result = new Section();

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();

		return result;
	}

	public Section findOne(final int sectionId) {
		Section result;

		result = this.sectionRepository.findOne(sectionId);

		return result;
	}

	public void save(final Section section) {
		Assert.notNull(section);

		this.sectionRepository.save(section);
	}

	public void delete(final Section section) {
		this.sectionRepository.delete(section);
	}

	public Collection<Section> findByTutorial(final int id) {
		Collection<Section> res = new ArrayList<>();
		res = this.sectionRepository.findByTutorial(id);
		return res;
	}

	public Section reconstruct(final Tutorial tutorial, final Section section, final BindingResult binding) {
		final Section res = section;
		final Section s = this.findOne(section.getId());
		if (section.getId() == 0)
			res.setTutorial(tutorial);
		else
			res.setTutorial(s.getTutorial());
		this.validator.validate(res, binding);
		return res;
	}
}
