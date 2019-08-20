
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Conference;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;


	public Collection<Submission> findSubmissionsByConference(final Conference conference) {
		return this.submissionRepository.findSubmissionsByConference(conference.getId());
	}

	public Submission save(final Submission submission) {
		final Submission result = this.submissionRepository.save(submission);
		return result;

	}

	public Collection<Submission> findAcceptedSubmissionsByConference(final Conference conference) {
		return this.submissionRepository.findAcceptedSubmissionsByConference(conference.getId());
	}

	public Collection<Submission> findRejectedSubmissionsByConference(final Conference conference) {
		return this.submissionRepository.findRejectedSubmissionsByConference(conference.getId());
	}

	public Collection<Double> statsSubmissionsPerConference() {
		final Collection<Double> result = this.submissionRepository.statsSubmissionsPerConference();
		Assert.notNull(result);
		return result;
	}

}
