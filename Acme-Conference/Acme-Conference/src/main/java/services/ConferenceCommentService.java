
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceCommentRepository;
import domain.ConferenceComment;

@Service
@Transactional
public class ConferenceCommentService {

	@Autowired
	private ConferenceCommentRepository	conferenceCommentRepository;


	//COnstructors -------------------------
	public ConferenceCommentService() {
		super();
	}

	public ConferenceComment create() {
		ConferenceComment result;

		result = new ConferenceComment();

		return result;
	}

	public Collection<ConferenceComment> findAll() {
		Collection<ConferenceComment> result;

		result = this.conferenceCommentRepository.findAll();

		return result;
	}

	public ConferenceComment findOne(final int conferenceCommentId) {
		ConferenceComment result;

		result = this.conferenceCommentRepository.findOne(conferenceCommentId);

		return result;
	}

	public void save(final ConferenceComment conferenceComment) {
		Assert.notNull(conferenceComment);

		this.conferenceCommentRepository.save(conferenceComment);
	}

	public void delete(final ConferenceComment conferenceComment) {
		this.conferenceCommentRepository.delete(conferenceComment);
	}

	public Collection<Double> statsCommentsPerConference() {
		final Collection<Double> result = this.conferenceCommentRepository.statsCommentsPerConference();
		Assert.notNull(result);
		return result;
	}

}
