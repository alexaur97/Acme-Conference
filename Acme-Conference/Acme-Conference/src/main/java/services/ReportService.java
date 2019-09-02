
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import domain.Author;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private Validator			validator;


	public Report create() {
		Report result;
		result = new Report();
		return result;
	}

	public Collection<Report> findReportsBySubmission(final Submission s) {
		return this.reportRepository.findReportsBySubmission(s.getId());
	}

	public Collection<Report> findAcceptReportsBySubmission(final Submission s) {
		return this.reportRepository.findAcceptReportsBySubmission(s.getId());
	}

	public Collection<Report> findRejectReportsBySubmission(final Submission s) {
		return this.reportRepository.findRejectReportsBySubmission(s.getId());
	}

	public Collection<Report> findReportsInAcceptedSubmission(final Submission s) {
		final Author principal = this.authorService.findByPrincipal();
		return this.reportRepository.findReportsInAcceptedSubmission(s.getId(), principal.getId());
	}

	public Report findOne(final int reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public Report reconstruct(final Report report, final BindingResult binding) {
		final Report res = report;
		final Reviewer reviewer = this.reviewerService.findByPrincipal();
		final Submission submission = reviewer.getSubmission();
		res.setReviewer(reviewer);
		res.setSubmission(submission);

		this.validator.validate(res, binding);
		return res;
	}

	public Report save(final Report report) {
		return this.reportRepository.save(report);

	}

	public Collection<Report> findReportsByPrincipal() {
		final Reviewer principal = this.reviewerService.findByPrincipal();
		final int id = principal.getId();
		return this.reportRepository.findReportsByPrincipal(id);
	}

}
