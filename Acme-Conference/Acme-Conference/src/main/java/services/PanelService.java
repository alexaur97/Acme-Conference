
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PanelRepository;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	@Autowired
	private PanelRepository	panelRepository;

	@Autowired
	private Validator		validator;


	//COnstructors -------------------------
	public PanelService() {
		super();
	}

	public Panel create() {
		Panel result;

		result = new Panel();

		return result;
	}

	public Collection<Panel> findAll() {
		Collection<Panel> result;

		result = this.panelRepository.findAll();

		return result;
	}

	public Panel findOne(final int panelId) {
		Panel result;

		result = this.panelRepository.findOne(panelId);

		return result;
	}

	public void save(final Panel panel) {
		Assert.notNull(panel);

		this.panelRepository.save(panel);
	}

	public void delete(final Panel panel) {
		this.panelRepository.delete(panel);
	}

	public Collection<Panel> findByConference(final int id) {
		Collection<Panel> res = new ArrayList<>();
		res = this.panelRepository.findByConference(id);
		return res;
	}

	public Panel reconstruct(final Panel panel, final BindingResult binding) {
		final Panel res = panel;
		this.validator.validate(res, binding);
		return res;
	}
}
