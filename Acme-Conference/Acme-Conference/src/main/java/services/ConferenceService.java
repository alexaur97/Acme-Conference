
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	//Managed repository -------------------
	@Autowired
	private ConferenceRepository			conferenceRepository;

	//Supporting Services ------------------
	@Autowired
	private ConfigurationParametersService	configParamsService;

	@Autowired
	private Validator						validator;


	//COnstructors -------------------------
	public ConferenceService() {
		super();
	}

	//Simple CRUD methods--------------------

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

	//Other Methods--------------------
	public Collection<Conference> searchConference(final String keyword) {
		return this.conferenceRepository.searchConferencesKeyWord(keyword);
	}

	public Collection<Conference> findSubmissionLastFiveDays() {
		final Date actualDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(actualDate); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date lastDate = calendar.getTime();

		return this.conferenceRepository.findSubmissionLastFiveDays(lastDate, actualDate);
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

}
