
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	//Managed repository -------------------
	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private AuthorService		authorService;


	//COnstructors -------------------------
	public FinderService() {
		super();
	}

	//Simple CRUD methods--------------------

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);

		return result;
	}

	public void delete(final Finder finder) {
		this.finderRepository.delete(finder);
	}

	//Other Methods--------------------

	public Finder getFinderFromAuthor(final int id) {
		return this.finderRepository.getFinderFromAuthor(id);
	}

	public void save(final Finder finder) {
		Assert.notNull(finder);
		finder.setAuthor(this.authorService.findByPrincipal());
		finder.setConferences(this.conferenceService.searchConferences(finder.getKeyword(), finder.getCategory(), finder.getMinDate(), finder.getMaxDate(), finder.getMaxFee()));
		this.finderRepository.save(finder);
	}

	public void saveForRegister(final Finder finder) {
		Assert.notNull(finder);
		this.finderRepository.save(finder);
	}
	public Finder reconstruct(final Finder finder) {
		Assert.notNull(finder);
		final Finder result = this.finderRepository.findOne(finder.getId());
		result.setKeyword(finder.getKeyword());
		result.setMaxDate(finder.getMaxDate());
		result.setMinDate(finder.getMinDate());
		result.setCategory(finder.getCategory());
		result.setMaxFee(finder.getMaxFee());
		return result;
	}

	public void saveAfterClean(final Finder finder) {
		Assert.notNull(finder);
		final Collection<Conference> conferences = new ArrayList<Conference>();
		finder.setAuthor(this.authorService.findByPrincipal());
		finder.setConferences(conferences);
		this.finderRepository.save(finder);
	}

	public void cleanFinder(final Finder finder) {
		Assert.notNull(finder);
		final Collection<Conference> conferences = new ArrayList<Conference>();
		finder.setKeyword("");
		finder.setMaxDate(null);
		finder.setMinDate(null);
		finder.setCategory(null);
		finder.setMaxFee(null);
		finder.setConferences(conferences);
		this.finderRepository.save(finder);
	}

}
