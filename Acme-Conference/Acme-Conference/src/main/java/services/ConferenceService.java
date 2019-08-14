
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import domain.Conference;
import domain.Report;
import domain.Submission;

@Service
@Transactional
public class ConferenceService {

	// Managed repository -------------------
	@Autowired
	private ConferenceRepository			conferenceRepository;

	// Supporting Services ------------------
	@Autowired
	private ConfigurationParametersService	configParamsService;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private SubmissionService				submissionService;

	@Autowired
	private ReportService					reportService;

	@Autowired
	private Validator						validator;


	// COnstructors -------------------------
	public ConferenceService() {
		super();
	}

	// Simple CRUD methods--------------------

	public void save(final Conference conference) {
		Assert.notNull(conference);

		this.conferenceRepository.save(conference);
	}

	public Collection<Conference> findAll() {
		Collection<Conference> result;

		result = this.conferenceRepository.findAll();

		return result;
	}

	public Conference findOne(final int conferenceId) {
		Conference result;

		result = this.conferenceRepository.findOne(conferenceId);

		return result;
	}

	// Other Methods--------------------
	public Collection<Conference> searchConference(final String keyword) {
		return this.conferenceRepository.searchConferencesKeyWord(keyword);
	}

	public Collection<Conference> findConference() {
		final Collection<Conference> res = new ArrayList<>();
		final Collection<Conference> conferencias = this.conferenceRepository.findAll();
		final List<Conference> c = new ArrayList<>(conferencias);
		final Date actual = new Date();
		for (int i = 0; i < c.size(); i++)
			if (c.get(i).getStartDate().after(actual))
				res.add(c.get(i));
		return res;

	}

	public Collection<Conference> findSubmissionLastFiveDays() {
		final Date actualDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(actualDate); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date lastDate = calendar.getTime();

		return this.conferenceRepository.findSubmissionLastFiveDays(lastDate, actualDate);
	}

	public Collection<Conference> findByCategory(final int categoryId) {
		Collection<Conference> res = new ArrayList<>();
		res = this.conferenceRepository.findByCateogry(categoryId);
		return res;
	}

	public Collection<Conference> findNotificationLessFiveDays() {
		final Date actualDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(actualDate); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 5);
		final Date nextDate = calendar.getTime();

		return this.conferenceRepository.findNotificationLessFiveDays(actualDate, nextDate);
	}

	public Collection<Conference> findCameraReadyLessFiveDays() {
		final Date actualDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(actualDate); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 5);
		final Date nextDate = calendar.getTime();

		return this.conferenceRepository.findCameraReadyLessFiveDays(actualDate, nextDate);
	}

	public Collection<Conference> findStartDateLessFiveDays() {
		final Date actualDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(actualDate); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 5);
		final Date nextDate = calendar.getTime();

		return this.conferenceRepository.findStartDateLessFiveDays(actualDate, nextDate);
	}

	public void decisionProcedure(final Conference conference) {
		this.administratorService.findByPrincipal();
		final Date currentDate = new Date();
		Assert.isTrue(conference.getSubmissionDeadline().before(currentDate));
		Collection<Submission> submissionsByConference = this.submissionService.findSubmissionsByConference(conference);
		for(Submission s : submissionsByConference) {
			Submission retrieved = s;
			Collection<Report> acceptReportsBySubmission = this.reportService.findAcceptReportsBySubmission(s);
			Collection<Report> rejectReportsBySubmission = this.reportService.findRejectReportsBySubmission(s);
			Integer decision = acceptReportsBySubmission.size()-rejectReportsBySubmission.size();
			if(decision>=0) { // Simplemente con este condicional se cumplen todas las condiciones del requisito 14.4
				retrieved.setStatus("ACCEPTED");
			}else {
				retrieved.setStatus("REJECTED");
			}
			this.submissionService.save(retrieved);
		}
	}

	public Collection<Double> statsConferencePerCategory(){
		final Collection<Double> result = this.conferenceRepository.statsConferencesPerCategory();
		Assert.notNull(result);
		return result;
	}
}
