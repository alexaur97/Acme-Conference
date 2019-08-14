package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Conference;
import domain.Submission;
import repositories.SubmissionRepository;

@Service
@Transactional
public class SubmissionService {
	
	@Autowired
	private SubmissionRepository submissionRepository;

	public Collection<Submission> findSubmissionsByConference(Conference conference) {
		return this.submissionRepository.findSubmissionsByConference(conference.getId());
	}

	public Submission save(Submission submission) {
		Submission result = this.submissionRepository.save(submission);
		return result;
		
	}

	public Collection<Submission> findAcceptedSubmissionsByConference(Conference conference) {
		return this.submissionRepository.findAcceptedSubmissionsByConference(conference.getId());
	}
	
	public Collection<Submission> findRejectedSubmissionsByConference(Conference conference) {
		return this.submissionRepository.findRejectedSubmissionsByConference(conference.getId());
	}

}
