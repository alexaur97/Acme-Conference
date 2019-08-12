
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityCommentRepository;
import domain.ActivityComment;

@Service
@Transactional
public class ActivityCommentService {

	@Autowired
	private ActivityCommentRepository	activityCommentRepository;


	//COnstructors -------------------------
	public ActivityCommentService() {
		super();
	}

	public ActivityComment create() {
		ActivityComment result;

		result = new ActivityComment();

		return result;
	}

	public Collection<ActivityComment> findAll() {
		Collection<ActivityComment> result;

		result = this.activityCommentRepository.findAll();

		return result;
	}

	public ActivityComment findOne(final int activityCommentId) {
		ActivityComment result;

		result = this.activityCommentRepository.findOne(activityCommentId);

		return result;
	}

	public void save(final ActivityComment activityComment) {
		Assert.notNull(activityComment);

		this.activityCommentRepository.save(activityComment);
	}

	public void delete(final ActivityComment activityComment) {
		this.activityCommentRepository.delete(activityComment);
	}

	public Collection<Double> statsCommentsPerActivity() {
		final Collection<Double> result = this.activityCommentRepository.statsCommentsPerActivity();
		Assert.notNull(result);
		return result;
	}

}
