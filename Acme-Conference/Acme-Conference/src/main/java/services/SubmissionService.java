
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Conference;
import domain.Paper;
import domain.Submission;
import forms.PaperForm;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;
	@Autowired
	private AuthorService			authorService;
	@Autowired
	private PaperService			paperService;


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
	public Submission findOne(final int id) {
		return this.submissionRepository.findOne(id);
	}
	public Submission reconstruction(final PaperForm paperForm) {
		final Paper paper = new Paper();
		paper.setAuthor(this.authorService.findByPrincipal());
		paper.setAuthorAlias(paperForm.getAuthorAlias());
		paper.setDocument(paperForm.getDocument());
		paper.setSummary(paperForm.getSummary());
		paper.setTitle(paperForm.getTitle());
		final Paper paperF = this.paperService.save(paper);
		final Submission submission = this.findOne(paperForm.getSubmissionId());
		submission.setCameraReady(paperF);
		return submission;

	}
}
