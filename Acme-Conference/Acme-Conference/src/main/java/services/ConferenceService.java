
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

}
