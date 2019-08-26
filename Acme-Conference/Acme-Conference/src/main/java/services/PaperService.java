
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PaperRepository;
import domain.Paper;
import forms.PaperForm;

@Service
@Transactional
public class PaperService {

	@Autowired
	private PaperRepository	paperRepository;
	@Autowired
	private AuthorService	authorService;

	@Autowired
	private Validator		validator;


	//COnstructors -------------------------
	public PaperService() {
		super();
	}

	public Paper create() {
		Paper result;

		result = new Paper();

		return result;
	}

	public Collection<Paper> findAll() {
		Collection<Paper> result;

		result = this.paperRepository.findAll();

		return result;
	}

	public Paper findOne(final int paperId) {
		Paper result;

		result = this.paperRepository.findOne(paperId);

		return result;
	}

	public Paper save(final Paper paper) {
		Assert.notNull(paper);

		final Paper result = this.paperRepository.save(paper);
		return result;
	}

	public void delete(final Paper paper) {
		this.paperRepository.delete(paper);
	}

	public Paper reconstruct(final Paper paper, final BindingResult binding) {
		final Paper res = paper;
		this.validator.validate(res, binding);
		return res;
	}
	public Paper reconstructionSub(final PaperForm paperForm, final BindingResult binding) {
		final Paper paper = new Paper();
		paper.setAuthor(this.authorService.findByPrincipal());
		paper.setAuthorAlias(paperForm.getAuthorAlias());
		paper.setDocument(paperForm.getDocument());
		paper.setSummary(paperForm.getSummary());
		paper.setTitle(paperForm.getTitle());

		return paper;
	}
}
