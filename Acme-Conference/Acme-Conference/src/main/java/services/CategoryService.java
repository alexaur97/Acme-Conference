
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

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;

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
