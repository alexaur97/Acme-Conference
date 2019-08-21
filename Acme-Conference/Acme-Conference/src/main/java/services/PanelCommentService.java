
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelCommentRepository;
import domain.PanelComment;

@Service
@Transactional
public class PanelCommentService {

	@Autowired
	private PanelCommentRepository	panelCommentRepository;


	//COnstructors -------------------------
	public PanelCommentService() {
		super();
	}

	public PanelComment create() {
		PanelComment result;

		result = new PanelComment();

		return result;
	}

	public Collection<PanelComment> findAll() {
		Collection<PanelComment> result;

		result = this.panelCommentRepository.findAll();

		return result;
	}

	public PanelComment findOne(final int panelCommentId) {
		PanelComment result;

		result = this.panelCommentRepository.findOne(panelCommentId);

		return result;
	}

	public PanelComment save(final PanelComment panelComment) {
		Assert.notNull(panelComment);

		return this.panelCommentRepository.save(panelComment);
	}

	public void delete(final PanelComment panelComment) {
		this.panelCommentRepository.delete(panelComment);
	}

	public Collection<Double> statsCommentsPerPanel() {
		final Collection<Double> result = this.panelCommentRepository.statsCommentsPerPanel();
		Assert.notNull(result);
		return result;
	}

	public Collection<PanelComment> listCommentByPanel(final int panelId) {
		final Collection<PanelComment> result = this.panelCommentRepository.listCommentsByPanel(panelId);
		return result;
	}
}
