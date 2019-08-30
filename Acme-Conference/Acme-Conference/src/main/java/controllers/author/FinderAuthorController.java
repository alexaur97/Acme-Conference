
package controllers.author;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.CategoryService;
import services.ConferenceService;
import services.FinderService;
import controllers.AbstractController;
import domain.Category;
import domain.Conference;
import domain.Finder;

@Controller
@RequestMapping("finder/author")
public class FinderAuthorController extends AbstractController {

	@Autowired
	AuthorService		authorService;

	@Autowired
	ConferenceService	positionService;

	@Autowired
	FinderService		finderService;

	@Autowired
	CategoryService		categoryService;


	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result = new ModelAndView("finder/view");

		try {
			final Finder finder = this.finderService.getFinderFromAuthor(this.authorService.findByPrincipal().getId());
			final Collection<Conference> conferences = finder.getConferences();
			final Collection<Category> categories = this.categoryService.findAll();
			result.addObject("categories", categories);
			result.addObject("conferences", conferences);
			result.addObject("finder", finder);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("requestURI", "finder/author/view.do");
			result.addObject("lang", lang);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, final BindingResult binding) {
		ModelAndView res = new ModelAndView("finder/view");
		final Collection<Conference> conferences = finder.getConferences();
		final Collection<Category> categories = this.categoryService.findAll();
		if (binding.hasErrors()) {
			res.addObject("conferences", conferences);
			res.addObject("finder", finder);
			res.addObject("categories", categories);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("requestURI", "finder/author/view.do");
			res.addObject("lang", lang);
		} else
			try {
				finder = this.finderService.reconstruct(finder);
				this.finderService.save(finder);
				res = new ModelAndView("redirect:/finder/author/view.do");
			} catch (final Throwable oops) {
				res.addObject("message", "finder.commit.error");
				res.addObject("conferences", conferences);
				res.addObject("finder", finder);
				res.addObject("categories", categories);
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("requestURI", "finder/author/view.do");
				res.addObject("lang", lang);
			}

		return res;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "clean")
	public ModelAndView clean(Finder finder, final BindingResult binding) {
		ModelAndView res = new ModelAndView("finder/view");
		final Collection<Conference> conferences = new ArrayList<Conference>();
		final Collection<Category> categories = this.categoryService.findAll();
		if (binding.hasErrors()) {
			res.addObject("conferences", conferences);
			res.addObject("finder", finder);
			res.addObject("categories", categories);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("requestURI", "finder/author/view.do");
			res.addObject("lang", lang);
		} else
			try {
				finder = this.finderService.reconstruct(finder);
				this.finderService.saveAfterClean(finder);
				res = new ModelAndView("redirect:/finder/author/view.do");
			} catch (final Throwable oops) {
				res.addObject("message", "finder.clean.commit.error");
				res.addObject("positions", conferences);
				res.addObject("finder", finder);
				res.addObject("categories", categories);
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("requestURI", "finder/author/view.do");
				res.addObject("lang", lang);
			}

		return res;
	}

}
