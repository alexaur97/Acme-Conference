
package controllers.administrator;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {

			this.administratorService.findByPrincipal();
			final Collection<Category> categories = this.categoryService.findAll();

			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();

			result = new ModelAndView("category/list");
			result.addObject("requestURI", "category/administrator/list.do");
			result.addObject("categories", categories);
			result.addObject("lang", lang);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			final Collection<Category> categories = this.categoryService.findAll();
			this.administratorService.findByPrincipal();
			final Category category = this.categoryService.create();
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			result.addObject("categories", categories);
			result.addObject("lang", lang);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {

		ModelAndView result;

		try {
			this.administratorService.findByPrincipal();
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			final Collection<Category> categories = this.categoryService.findAll();
			final Category category = this.categoryService.findOne(categoryId);
			Assert.notNull(category);
			Assert.isTrue(!category.getRoot());
			result = new ModelAndView("category/edit");
			result.addObject("lang", lang);
			result.addObject("category", category);
			result.addObject("categories", categories);

		} catch (final Throwable oops) {

			try {
				final Collection<Category> categories = this.categoryService.findAll();
				final Category category = this.categoryService.findOne(categoryId);
				Assert.notNull(category);

				if (category.getRoot() == true) {
					result = new ModelAndView("category/list");
					result.addObject("categories", categories);
					result.addObject("message", "category.root.error");

				} else
					result = new ModelAndView("redirect:/#");
			} catch (final Throwable oops2) {
				result = new ModelAndView("redirect:/#");
			}
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final Category category, final BindingResult binding) {

		ModelAndView result;
		this.administratorService.findByPrincipal();
		final Category categoryF = this.categoryService.reconstruct(category, binding);
		if (binding.hasErrors()) {
			final Collection<Category> categories = this.categoryService.findAll();
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			result.addObject("categories", categories);

		} else
			try {
				Assert.notNull(categoryF.getParent());
				this.categoryService.save(categoryF);
				result = new ModelAndView("redirect:/category/administrator/list.do");

			} catch (final Throwable oops) {
				final Collection<Category> categories = this.categoryService.findAll();
				if (categoryF.getParent() == null) {
					result = new ModelAndView("category/edit");
					result.addObject("category", category);
					result.addObject("categories", categories);
					result.addObject("message", "category.parent.error");
				} else{
					result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("categories", categories);
				result.addObject("message", "category.commit.error");
			}}
		return result;

	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category category, final BindingResult binding) {
		ModelAndView result;
		final Category res = this.categoryService.findOne(category.getId());
		Assert.notNull(res);

		try {
			Assert.isTrue(!res.getRoot());
			this.administratorService.findByPrincipal();

			this.categoryService.delete(res);
			result = new ModelAndView("redirect:/category/administrator/list.do");
		} catch (final Throwable oops) {
			final Collection<Category> categories = this.categoryService.findAll();

			if (res.getRoot() == true) {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("categories", categories);
				result.addObject("message", "category.root.error");
			} else {

				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("categories", categories);
				result.addObject("message", "category.commit.error");
			}
		}
		return result;
	}
}
