
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import domain.Category;
import domain.Conference;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private Validator			validator;


	//COnstructors -------------------------
	public CategoryService() {
		super();
	}

	public Category create() {
		Category result;

		result = new Category();

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();

		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);

		return result;
	}

	public void save(final Category category) {
		Assert.notNull(category);

		this.categoryRepository.save(category);
	}

	public void delete(final Category category) {

		final Collection<Conference> conferences = this.conferenceService.findByCategory(category.getId());
		for (final Conference c : conferences) {
			c.setCategory(c.getCategory().getParent());
			this.conferenceService.saveDeleteCategory(c);

		}
		final Collection<Category> childCategories = this.findCategoriesByParent(category.getId());
		for (final Category c : childCategories) {
			final Collection<Conference> childConferences = this.conferenceService.findByCategory(c.getId());
			for (final Conference cc : childConferences) {
				cc.setCategory(cc.getCategory().getParent().getParent());
				this.conferenceService.saveDeleteCategory(cc);
			}
			this.categoryRepository.delete(c);
		}

		this.categoryRepository.delete(category);
	}
	public Collection<Category> findCategoriesByParent(final int categoryId) {
		Collection<Category> res = new ArrayList<>();
		res = this.categoryRepository.categoriesByParent(categoryId);
		return res;
	}

	public Category reconstruct(final Category category, final BindingResult binding) {
		final Category res = category;
		res.setRoot(false);
		this.validator.validate(res, binding);
		return res;
	}
}
