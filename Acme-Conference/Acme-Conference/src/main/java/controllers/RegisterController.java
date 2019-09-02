
package controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ReviewerService;
import services.SponsorService;
import domain.Author;
import domain.Reviewer;
import domain.Sponsor;
import forms.RegisterReviewerForm;
import forms.RegisterSponsorAndAuthorForm;

@Controller
@RequestMapping("/actor")
public class RegisterController extends AbstractController {

	@Autowired
	private SponsorService	sponsorService;

	@Autowired
	private AuthorService	authorService;

	@Autowired
	private ReviewerService	reviewerService;


	@RequestMapping(value = "/registerSponsor", method = RequestMethod.GET)
	public ModelAndView registerSponsor() {
		ModelAndView res;
		try {
			final RegisterSponsorAndAuthorForm registerSponsorAndAuthorForm = new RegisterSponsorAndAuthorForm();
			res = new ModelAndView("actor/registerSponsor");
			res.addObject("registerSponsorAndAuthorForm", registerSponsorAndAuthorForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		}

		return res;
	}

	@RequestMapping(value = "/registerSponsor", method = RequestMethod.POST, params = "saveSponsor")
	public ModelAndView saveSponsor(@Valid final RegisterSponsorAndAuthorForm registerSponsorAndAuthorForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/registerSponsor");
			res.addObject("registerSponsorAndAuthorForm", registerSponsorAndAuthorForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		} else
			try {
				final Sponsor sponsor = this.sponsorService.reconstruct(registerSponsorAndAuthorForm);
				this.sponsorService.save(sponsor);
				res = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {

				res = new ModelAndView("actor/registerSponsor");
				res.addObject("registerSponsorAndAuthorForm", registerSponsorAndAuthorForm);
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("lang", lang);
				if (oops.getMessage().equals("register.password.error"))
					res.addObject("message", "register.password.error");
				if (oops.getClass().equals(DataIntegrityViolationException.class))
					res.addObject("message", "register.username.error");
				if (oops.getMessage().equalsIgnoreCase("assertCheck"))
					res.addObject("message", "register.check.error");
				if (oops.getMessage().equals("register.username.error"))
					res.addObject("message", "register.username.error");

			}

		return res;
	}

	@RequestMapping(value = "/registerAuthor", method = RequestMethod.GET)
	public ModelAndView registerAuthor() {
		ModelAndView res;
		try {
			final RegisterSponsorAndAuthorForm registerSponsorAndAuthorForm = new RegisterSponsorAndAuthorForm();
			res = new ModelAndView("actor/registerAuthor");
			res.addObject("registerSponsorAndAuthorForm", registerSponsorAndAuthorForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		}

		return res;
	}

	@RequestMapping(value = "/registerAuthor", method = RequestMethod.POST, params = "saveAuthor")
	public ModelAndView saveAuthor(@Valid final RegisterSponsorAndAuthorForm registerSponsorAndAuthorForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/registerAuthor");
			res.addObject("registerSponsorAndAuthorForm", registerSponsorAndAuthorForm);
		} else
			try {
				final Author author = this.authorService.reconstruct(registerSponsorAndAuthorForm);
				;
				this.authorService.assignFinderToAuthor(this.authorService.save(author).getId());
				res = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {

				res = new ModelAndView("actor/registerAuthor");
				res.addObject("registerSponsorAndAuthorForm", registerSponsorAndAuthorForm);
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("lang", lang);
				if (oops.getMessage().equals("register.password.error"))
					res.addObject("message", "register.password.error");
				if (oops.getClass().equals(DataIntegrityViolationException.class))
					res.addObject("message", "register.username.error");
				if (oops.getMessage().equalsIgnoreCase("assertCheck"))
					res.addObject("message", "register.check.error");
				if (oops.getMessage().equals("register.username.error"))
					res.addObject("message", "register.username.error");
			}

		return res;
	}

	@RequestMapping(value = "/registerReviewer", method = RequestMethod.GET)
	public ModelAndView registerReviewer() {
		ModelAndView res;
		try {
			final RegisterReviewerForm registerReviewerForm = new RegisterReviewerForm();
			res = new ModelAndView("actor/registerReviewer");
			res.addObject("registerReviewerForm", registerReviewerForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		}

		return res;
	}

	@RequestMapping(value = "/registerReviewer", method = RequestMethod.POST, params = "saveReviewer")
	public ModelAndView saveReviewer(@Valid final RegisterReviewerForm registerReviewerForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/registerReviewer");
			res.addObject("registerReviewerForm", registerReviewerForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);
		} else
			try {
				final Reviewer reviewer = this.reviewerService.reconstruct(registerReviewerForm);
				this.reviewerService.save(reviewer);
				res = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {

				res = new ModelAndView("actor/registerReviewer");
				res.addObject("registerReviewerForm", registerReviewerForm);
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("lang", lang);
				if (oops.getMessage().equals("register.password.error"))
					res.addObject("message", "register.password.error");
				if (oops.getClass().equals(DataIntegrityViolationException.class))
					res.addObject("message", "register.username.error");
				if (oops.getMessage().equalsIgnoreCase("assertCheck"))
					res.addObject("message", "register.check.error");
				if (oops.getMessage().equals("register.username.error"))
					res.addObject("message", "register.username.error");
			}

		return res;
	}
}
