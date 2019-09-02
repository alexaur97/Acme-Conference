
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import domain.Administrator;
import domain.Author;
import domain.Category;
import domain.Conference;
import domain.Message;
import domain.Report;
import domain.Submission;
import domain.Topic;

@Service
@Transactional
public class ConferenceService {

	// Managed repository -------------------
	@Autowired
	private ConferenceRepository	conferenceRepository;

	// Supporting Services ------------------
	@Autowired
	private TopicService			topicService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ReviewerService			reviewerService;


	// COnstructors -------------------------
	public ConferenceService() {
		super();
	}

	// Simple CRUD methods--------------------

	public void save(final Conference conference) {
		Assert.notNull(conference);
		this.administratorService.findByPrincipal();

		if (conference.getId() != 0) {
			final Conference confDB = this.findOne(conference.getId());
			Assert.isTrue(confDB.getMode().equals("DRAFT"));
		}
		Assert.isTrue(conference.getSubmissionDeadline().after(new Date()));
		Assert.isTrue(conference.getSubmissionDeadline().before(conference.getNotification()));
		Assert.isTrue(conference.getNotification().before(conference.getCameraReady()));
		Assert.isTrue(conference.getCameraReady().before(conference.getStartDate()));
		Assert.isTrue(conference.getStartDate().before(conference.getEndDate()));

		this.conferenceRepository.save(conference);
	}

	public void saveDeleteCategory(final Conference conference) {
		Assert.notNull(conference);
		this.administratorService.findByPrincipal();
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

	public Collection<Conference> findNextConferences() {
		final Date date = new Date();
		final Collection<Conference> res = this.conferenceRepository.findNextConferences(date);
		//		final Collection<Conference> res = new ArrayList<>();
		//		final Collection<Conference> conferencias = this.conferenceRepository.findAll();
		//		final List<Conference> c = new ArrayList<>(conferencias);
		//		final Date actual = new Date();
		//		for (int i = 0; i < c.size(); i++)
		//			if (c.get(i).getStartDate().after(actual))
		//				res.add(c.get(i));
		return res;

	}

	public Collection<Conference> findPastConference() {
		final Collection<Conference> conferencias = this.conferenceRepository.findPastConference(new Date());
		return conferencias;

	}
	public Collection<Conference> findRunningConference() {
		final Collection<Conference> conferencias = this.conferenceRepository.findRunningConference(new Date());
		return conferencias;

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
		final Administrator admin = this.administratorService.findByPrincipal();
		final Date currentDate = new Date();
		Assert.isTrue(conference.getSubmissionDeadline().before(currentDate));
		final Collection<Submission> submissionsByConference = this.submissionService.findSubmissionsByConference(conference);
		Assert.isTrue(!submissionsByConference.isEmpty());
		for (final Submission s : submissionsByConference) {
			final Submission retrieved = s;
			final Collection<Report> acceptReportsBySubmission = this.reportService.findAcceptReportsBySubmission(s);
			final Collection<Report> rejectReportsBySubmission = this.reportService.findRejectReportsBySubmission(s);
			final Integer decision = acceptReportsBySubmission.size() - rejectReportsBySubmission.size();
			final Message adm = this.messageService.create();
			final Message notification = this.messageService.create();
			if (decision >= 0) { // Simplemente con este condicional se cumplen todas las condiciones del
									// requisito 14.4
				retrieved.setStatus("ACCEPTED");
				adm.setSubject("Accepted submission");
				adm.setBody("Your submission to the conference " + s.getConference().getTitle() + " has been accepted.\nSu presentaci�n a la conferencia " + s.getConference().getTitle() + " ha sido aceptada.\n\nTicker: " + s.getTicker());
				notification.setSubject("Accepted submission");
				notification.setBody("Your submission to the conference " + s.getConference().getTitle() + " has been accepted.\nSu presentaci�n a la conferencia " + s.getConference().getTitle() + " ha sido aceptada.\n\nTicker: " + s.getTicker());
			} else {
				retrieved.setStatus("REJECTED");
				adm.setSubject("Rejected submission");
				adm.setBody("Your submission to the conference " + s.getConference().getTitle() + " has been rejected.\nSu presentaci�n a la conferencia " + s.getConference().getTitle() + " ha sido rechazada.\n\nTicker: " + s.getTicker());
				notification.setSubject("Rejected submission");
				notification.setBody("Your submission to the conference " + s.getConference().getTitle() + " has been rejected.\nSu presentaci�n a la conferencia " + s.getConference().getTitle() + " ha sido rechazada.\n\nTicker: " + s.getTicker());
			}

			adm.setCopy(false);
			adm.setDeleted(false);
			adm.setMoment(currentDate);
			notification.setCopy(true);
			notification.setDeleted(false);
			notification.setMoment(currentDate);

			final Author author = retrieved.getAuthor();

			adm.setOwner(admin);
			adm.setRecipient(author);
			adm.setSender(admin);
			adm.setSpam(false);
			notification.setOwner(author);
			notification.setRecipient(author);
			notification.setSender(admin);
			notification.setSpam(false);

			final Topic topic = this.topicService.findOtherTopic();
			if (topic != null) {
				adm.setTopic(topic);
				notification.setTopic(topic);
			} else {
				final Topic t = new Topic();
				t.setName("OTHER");
				t.setNameEs("OTROS");
				final Topic saved = this.topicService.save(t);
				adm.setTopic(saved);
				notification.setTopic(saved);
			}
			this.submissionService.save(retrieved);
			this.messageService.save(adm);
			this.messageService.save(notification);
		}
	}

	public Collection<Double> statsConferencePerCategory() {
		final Collection<Double> result = this.conferenceRepository.statsConferencesPerCategory();
		Assert.notNull(result);
		return result;
	}

	public Collection<Double> statsConferencesFee() {
		final Collection<Double> result = this.conferenceRepository.statsConferencesFee();
		Assert.notNull(result);
		return result;
	}

	public Collection<Double> statsConferencesDays() {
		final Collection<Double> result = this.conferenceRepository.statsConferencesDays();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findOpenConferences() {
		final Collection<Conference> result = this.conferenceRepository.findOpenConferences(new Date());
		Assert.notNull(result);
		return result;
	}
	public Conference create() {
		final Conference res = new Conference();
		return res;
	}

	public Conference reconstruct(final Conference conference, final BindingResult binding) {
		final Conference res = conference;

		if (conference.getId() != 0) {
			final Conference confDB = this.findOne(conference.getId());
			Assert.isTrue(confDB.getMode().equals("DRAFT"));
		}
		final Administrator adm = this.administratorService.findByPrincipal();
		res.setAdministrator(adm);

		this.validator.validate(res, binding);

		return res;
	}

	public Collection<Conference> conferencesDraft() {

		final Collection<Conference> res = this.conferenceRepository.conferencesDraft();
		return res;
	}

	public Collection<Conference> findConferencesFinal() {

		final Collection<Conference> res = this.conferenceRepository.findConferencesFinal();
		return res;
	}

	public Collection<Conference> searchConferences(final String keyword, final Category category, final Date minDate, final Date maxDate, final Double maxFee) {
		Collection<Conference> conferencesByKeyWord = new ArrayList<>();
		Collection<Conference> conferencesByCategory = new ArrayList<>();
		Collection<Conference> conferencesByMinDate = new ArrayList<>();
		Collection<Conference> conferencesByMaxDate = new ArrayList<>();
		Collection<Conference> conferencesByMaxFee = new ArrayList<>();
		Collection<Conference> result = new ArrayList<>();
		if (keyword.isEmpty())
			conferencesByKeyWord = this.findConferencesFinal();
		else
			conferencesByKeyWord = this.searchConference(keyword);
		if (Objects.equals(null, minDate))
			conferencesByMinDate = this.findConferencesFinal();
		else
			conferencesByMinDate = this.conferenceRepository.findNextConferences(minDate);

		if (Objects.equals(null, maxDate))
			conferencesByMaxDate = this.findConferencesFinal();
		else
			conferencesByMaxDate = this.conferenceRepository.findPastConference(maxDate);

		if (Objects.equals(null, maxFee))
			conferencesByMaxFee = this.findConferencesFinal();
		else
			conferencesByMaxFee = this.conferenceRepository.findConferencesByMaxFee(maxFee);

		if (Objects.equals(null, category))
			conferencesByCategory = this.findConferencesFinal();
		else
			conferencesByCategory = this.findByCategory(category.getId());

		conferencesByKeyWord.retainAll(conferencesByMinDate);
		conferencesByKeyWord.retainAll(conferencesByMaxDate);
		conferencesByKeyWord.retainAll(conferencesByMaxFee);
		conferencesByKeyWord.retainAll(conferencesByCategory);
		result = conferencesByKeyWord;
		return result;

	}
}
