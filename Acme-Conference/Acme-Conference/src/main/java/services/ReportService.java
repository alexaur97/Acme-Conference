package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Report;
import domain.Submission;
import repositories.ReportRepository;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	public Collection<Report> findReportsBySubmission(Submission s) {
		return this.reportRepository.findReportsBySubmission(s.getId());
	}

	public Collection<Report> findAcceptedReportsBySubmission(Submission s) {
		return this.reportRepository.findAcceptedReportsBySubmission(s.getId());
	}

}
