package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import domain.Author;
import domain.Report;
import domain.Reviewer;
import domain.Submission;
import repositories.AuthorRepository;
import repositories.ReportRepository;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ReviewerService reviewerService;
	
	public Report create() {
		return new Report();
	}

	public Collection<Report> findReportsBySubmission(Submission s) {
		return this.reportRepository.findReportsBySubmission(s.getId());
	}

	public Collection<Report> findAcceptReportsBySubmission(Submission s) {
		return this.reportRepository.findAcceptReportsBySubmission(s.getId());
	}

	public Collection<Report> findRejectReportsBySubmission(Submission s) {
		return this.reportRepository.findRejectReportsBySubmission(s.getId());
	}

	public Collection<Report> findReportsInAcceptedSubmission(Submission s) {
		Author principal = this.authorService.findByPrincipal();
		return this.reportRepository.findReportsInAcceptedSubmission(s.getId(), principal.getId());
	}

	public Report findOne(int reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public Report reconstruct(Report report, BindingResult binding) {
		// TODO Auto-generated method stub
		return null;
	}

	public Report save(Report report) {
		return this.reportRepository.save(report);
		
	}

	public Collection<Report> findReportsByPrincipal() {
		Reviewer principal = this.reviewerService.findByPrincipal();
		return this.reportRepository.findReportsByPrincipal(principal);
	}

}
