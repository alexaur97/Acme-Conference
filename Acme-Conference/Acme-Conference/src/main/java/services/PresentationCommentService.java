
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationCommentRepository;
import domain.PresentationComment;

@Service
@Transactional
public class PresentationCommentService {

	@Autowired
	private PresentationCommentRepository	presentationCommentRepository;


	//COnstructors -------------------------
	public PresentationCommentService() {
		super();
	}

	public PresentationComment create() {
		PresentationComment result;

		result = new PresentationComment();

		return result;
	}

	public Collection<PresentationComment> findAll() {
		Collection<PresentationComment> result;

		result = this.presentationCommentRepository.findAll();

		return result;
	}

	public PresentationComment findOne(final int presentationCommentId) {
		PresentationComment result;

		result = this.presentationCommentRepository.findOne(presentationCommentId);

		return result;
	}

	public PresentationComment save(final PresentationComment presentationComment) {
		Assert.notNull(presentationComment);

		return this.presentationCommentRepository.save(presentationComment);
	}

	public void delete(final PresentationComment presentationComment) {
		this.presentationCommentRepository.delete(presentationComment);
	}

	public Collection<Double> statsCommentsPerPresentation() {
		final Collection<Double> result = this.presentationCommentRepository.statsCommentsPerPresentation();
		Assert.notNull(result);
		return result;
	}

	public Collection<PresentationComment> listCommentByPresentation(final int presentationId) {
		final Collection<PresentationComment> result = this.presentationCommentRepository.listCommentPresentation(presentationId);
		return result;
	}

}
