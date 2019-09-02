
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PaperRepository;
import domain.Actor;
import domain.Author;
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
	public Collection<String> buzzWords() {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		final Collection<Paper> papers = this.findAll();
		final Collection<String> res = new ArrayList<String>();
		for (final Paper paper : papers) {
			final String[] arrayTitle = paper.getTitle().split(" ");
			final List<String> wordsTitle = Arrays.asList(arrayTitle);
			for (final String wordTitle : wordsTitle)
				if (map.containsKey(wordTitle))
					map.put(wordTitle, map.get(wordTitle) + 1);
				else
					map.put(wordTitle, 1);

			final String[] arraySummary = paper.getSummary().split(" ");
			final List<String> wordsSummary = Arrays.asList(arraySummary);
			for (final String wordSummary : wordsSummary)
				if (map.containsKey(wordSummary))
					map.put(wordSummary, map.get(wordSummary) + 1);
				else
					map.put(wordSummary, 1);

		}
		final Collection<Integer> maxC = map.values();
		Double max = 0.0;
		for (final Integer integer : maxC)
			if (integer > max)
				max = integer.doubleValue();

		final Double limite = max - max * 0.2;
		final Collection<String> list = map.keySet();
		for (final String key : list)
			if (map.get(key) >= limite)
				res.add(key);
		return res;
	}
	public List<String> statsAuthors() {
		final List<String> res = new ArrayList<String>();
		//	final Collection<String> buzz = this.buzzWords();
		final Collection<Author> autores = this.authorService.findAll();

		for (final Author author : autores) {
			final int puntuacion = this.statsAuthor(author);
			//			int puntuacion = 0;
			//			final Collection<Paper> papers = this.findByAuthor(author.getId());
			//			for (final Paper paper : papers) {
			//				final String[] arrayTitle = paper.getTitle().split(" ");
			//				final List<String> wordsTitle = Arrays.asList(arrayTitle);
			//				for (final String wordTitle : wordsTitle)
			//					if (buzz.contains(wordTitle))
			//						puntuacion++;
			//				final String[] arraySummary = paper.getSummary().split(" ");
			//				final List<String> wordsSummary = Arrays.asList(arraySummary);
			//				for (final String wordSummary : wordsSummary)
			//					if (buzz.contains(wordSummary))
			//						puntuacion++;
			//		}
			final String authorPuntuacion = author.getName() + ":" + puntuacion;
			res.add(authorPuntuacion);
		}
		return res;
	}
	public int statsAuthor(final Actor author) {
		final Collection<String> buzz = this.buzzWords();
		int puntuacion = 0;
		final Collection<Paper> papers = this.findByAuthor(author.getId());
		for (final Paper paper : papers) {
			final String[] arrayTitle = paper.getTitle().split(" ");
			final List<String> wordsTitle = Arrays.asList(arrayTitle);
			for (final String wordTitle : wordsTitle)
				if (buzz.contains(wordTitle))
					puntuacion++;
			final String[] arraySummary = paper.getSummary().split(" ");
			final List<String> wordsSummary = Arrays.asList(arraySummary);
			for (final String wordSummary : wordsSummary)
				if (buzz.contains(wordSummary))
					puntuacion++;
		}
		return puntuacion;
	}
	public Collection<Paper> findByAuthor(final int id) {
		return this.paperRepository.findByAuthor(id);
	}

}
