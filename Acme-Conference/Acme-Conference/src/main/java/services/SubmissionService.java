
package services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Conference;
import domain.Paper;
import domain.Report;
import domain.Reviewer;
import domain.Submission;
import forms.MakeSubmissionForm;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;
	@Autowired
	private AuthorService			authorService;
	@Autowired
	private PaperService			paperService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private ReportService			reportService;


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
	public Submission reconstruction(final Integer id, final Paper paperForm) {
		final Paper paperF = this.paperService.save(paperForm);
		final Submission submissionF = this.findOne(id);
		final Submission submission = new Submission();
		submission.setAuthor(submissionF.getAuthor());
		submission.setConference(submissionF.getConference());
		submission.setId(submissionF.getId());
		submission.setMoment(submissionF.getMoment());
		submission.setCameraReady(paperF);
		submission.setPaper(submissionF.getPaper());
		submission.setStatus(submissionF.getStatus());
		submission.setTicker(submissionF.getTicker());
		submission.setVersion(submissionF.getVersion());
		return submission;

	}

	public Collection<Submission> findMySubmissions() {
		return this.submissionRepository.findMySubmissions(this.actorService.findByPrincipal().getId());
	}

	public Submission reconstructSubmission(final MakeSubmissionForm form) {
		final Submission submission = new Submission();
		submission.setAuthor(this.authorService.findOne(this.actorService.findByPrincipal().getId()));
		submission.setConference(form.getConference());
		submission.setTicker(this.generateTicker());
		submission.setMoment(new Date());
		submission.setStatus("UNDER-REVIEW");

		final Paper auxPaper = new Paper();
		auxPaper.setAuthor(this.authorService.findOne(this.actorService.findByPrincipal().getId()));
		auxPaper.setAuthorAlias(form.getAuthorAlias());
		auxPaper.setDocument(form.getDocument());
		auxPaper.setSummary(form.getSummary());
		auxPaper.setTitle(form.getTitle());
		submission.setPaper(this.paperService.save(auxPaper));

		return submission;
	}

	public String generateTicker() {
		String result = "";
		String middleNameInitial;

		final String nameInitial = String.valueOf(this.actorService.findByPrincipal().getName().charAt(0)).toUpperCase();
		if (this.actorService.findByPrincipal().getMiddleName() != null)
			middleNameInitial = String.valueOf(this.actorService.findByPrincipal().getMiddleName().charAt(0)).toUpperCase();
		else
			middleNameInitial = "X";
		final String surnameInitial = String.valueOf(this.actorService.findByPrincipal().getSurname().charAt(0)).toUpperCase();

		final String randomCode = this.generateRandomString(4);

		result += nameInitial + middleNameInitial + surnameInitial + "-" + randomCode;

		return result;
	}

	public String generateRandomString(final int length) {
		final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
		final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
		final String NUMBER = "0123456789";
		final String DATA_FOR_RANDOM_STRING = CHAR_UPPER + NUMBER;
		final SecureRandom random = new SecureRandom();

		if (length < 1)
			throw new IllegalArgumentException();

		final StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			// 0-62 (exclusive), random returns 0-61
			final int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			final char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

			sb.append(rndChar);

		}

		return sb.toString();

	}

	public Collection<Submission> findSubmissionUnderReview() {
		final Collection<Submission> res = this.submissionRepository.findSubmissionUnderReview();
		return res;
	}
	public Collection<Submission> findSubmissionAccepted() {
		final Collection<Submission> res = this.submissionRepository.findSubmissionAccepted();
		return res;
	}

	public Collection<Submission> findSubmissionRejected() {
		final Collection<Submission> res = this.submissionRepository.findSubmissionRejected();
		return res;
	}

	public String assign(final Submission submission) {
		this.administratorService.findByPrincipal();
		final String title = submission.getConference().getTitle();
		final String summary = submission.getConference().getSummary();
		final Collection<Reviewer> reviewers = this.reviewerService.findWithoutSubmission();
		int i = 0;

		for (final Reviewer reviewer : reviewers) {
			if (!this.hasAssigned(reviewer, submission))
				for (final String keyword : reviewer.getKeyWords()) {
					final int a = title.indexOf(keyword);
					final int b = summary.indexOf(keyword);
					if ((a != -1 || b != -1)) {
						reviewer.setSubmission(submission);
						i++;
						break;
					}

				}
			if (i == 3)
				break;
		}

		if (i == 3)
			return "submission.allReviewer";
		else if (i > 0)
			return "submission.notAllReviewer";
		else
			return "submission.unassigned";

	}

	private Boolean hasAssigned(final Reviewer reviewer, final Submission submission) {
		final Collection<Report> reports = this.reportService.findReportsBySubmission(submission);
		Boolean res = false;
		for (final Report rep : reports)
			if (rep.getReviewer().equals(reviewer)) {
				res = true;
				break;
			}
		return res;

	}
	public Collection<Submission> findAll() {
		final Collection<Submission> res = this.submissionRepository.findAll();
		return res;

	}

	public Collection<Submission> findUnassigned() {
		final Collection<Submission> assigned = this.submissionRepository.findAssigned();
		final Collection<Submission> all = this.findSubmissionUnderReview();
		final Collection<Submission> res = new ArrayList<>();
		for (final Submission subm : all)
			if (!assigned.contains(subm))
				res.add(subm);
		return res;
	}

	public Collection<Submission> findFromReportReviewer(final int id) {
		final Collection<Submission> res = this.submissionRepository.findSubmissionsFromReport(id);
		return res;
	}
}
