
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialCommentRepository;
import domain.TutorialComment;

@Service
@Transactional
public class TutorialCommentService {

	@Autowired
	private TutorialCommentRepository	tutorialCommentRepository;


	//COnstructors -------------------------
	public TutorialCommentService() {
		super();
	}

	public TutorialComment create() {
		TutorialComment result;

		result = new TutorialComment();

		return result;
	}

	public Collection<TutorialComment> findAll() {
		Collection<TutorialComment> result;

		result = this.tutorialCommentRepository.findAll();

		return result;
	}

	public TutorialComment findOne(final int tutorialCommentId) {
		TutorialComment result;

		result = this.tutorialCommentRepository.findOne(tutorialCommentId);

		return result;
	}

	public TutorialComment save(final TutorialComment tutorialComment) {
		Assert.notNull(tutorialComment);

		return this.tutorialCommentRepository.save(tutorialComment);
	}

	public void delete(final TutorialComment tutorialComment) {
		this.tutorialCommentRepository.delete(tutorialComment);
	}

	public Collection<Double> statsCommentsPerTutorial() {
		final Collection<Double> result = this.tutorialCommentRepository.statsCommentsPerTutorial();
		Assert.notNull(result);
		return result;
	}

	public Collection<TutorialComment> listCommentByTutorial(final int tutorialId) {
		final Collection<TutorialComment> result = this.tutorialCommentRepository.listCommentsByTutorial(tutorialId);
		return result;
	}
}
