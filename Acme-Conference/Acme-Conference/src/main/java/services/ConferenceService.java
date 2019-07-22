
package services;

import java.util.Collection;

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

}
