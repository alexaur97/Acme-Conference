
package controllers.all;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.AuthorService;
import services.PaperService;
import services.ReviewerService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Reviewer;
import domain.Sponsor;
import forms.ActorEditForm;
import forms.AdministratorEditForm;
import forms.ReviewerEditForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private PaperService			paperService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final Actor actor = this.actorService.findByPrincipal();

			result = new ModelAndView("actor/show");
			result.addObject("actor", actor);
			try {
				this.actorService.auth(actor, Authority.AUTHOR);
				final int score = this.paperService.statsAuthor(actor);
				if (score > 0)
					result.addObject("score", score);
			}catch(Throwable oops) {
				System.out.println(oops);
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findByPrincipal();
			result = new ModelAndView("actor/edit");
			if (this.actorService.authEdit(actor, "ADMINISTRATOR")) {
				final AdministratorEditForm administratorEditForm = this.administratorService.toForm(actor);
				result.addObject("administratorEditForm", administratorEditForm);
			} else if (this.actorService.authEdit(actor, "REVIEWER")) {
				final Reviewer principal = this.reviewerService.findByPrincipal();
				final ReviewerEditForm reviewerEditForm = this.reviewerService.toForm(principal);
				result.addObject("reviewerEditForm", reviewerEditForm);
			} else {
				final ActorEditForm actorEditForm = this.actorService.toForm(actor);
				result.addObject("actorEditForm", actorEditForm);
			}
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorEditForm actorEditForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("actorEditForm", actorEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} else
			try {
				final Actor actor = this.actorService.findByPrincipal();
				if (this.actorService.authEdit(actor, "AUTHOR")) {
					final Author author = this.authorService.reconstructEdit(actorEditForm);
					this.authorService.save(author);
				} else if (this.actorService.authEdit(actor, "SPONSOR")) {
					final Sponsor sponsor = this.sponsorService.reconstructEdit(actorEditForm);
					this.sponsorService.save(sponsor);
				}
				result = new ModelAndView("redirect:/actor/show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				result.addObject("lang", lang);

				result.addObject("requestURI", "actor/edit.do");
				result.addObject("message", "actor.commit.error");
				if (oops.getMessage().equals("edition.email.error"))
					result.addObject("message", "edition.email.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView saveAdmin(@Valid final AdministratorEditForm administratorEditForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("administratorEditForm", administratorEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} else
			try {
				final Administrator administrator = this.administratorService.reconstructEdit(administratorEditForm);
				this.administratorService.save(administrator);

				result = new ModelAndView("redirect:/actor/show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				result.addObject("lang", lang);

				result.addObject("requestURI", "actor/edit.do");
				result.addObject("message", "actor.commit.error");
				if (oops.getMessage().equals("edition.email.error"))
					result.addObject("message", "edition.email.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveReviewer")
	public ModelAndView saveReviewer(@Valid final ReviewerEditForm reviewerEditForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("reviewerEditForm", reviewerEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} else
			try {
				final Reviewer reviewer = this.reviewerService.reconstructEdit(reviewerEditForm);
				this.reviewerService.save(reviewer);

				result = new ModelAndView("redirect:/actor/show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("actor/edit");
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				result.addObject("lang", lang);

				result.addObject("requestURI", "actor/edit.do");
				result.addObject("message", "actor.commit.error");
				if (oops.getMessage().equals("edition.email.error"))
					result.addObject("message", "edition.email.error");
			}
		return result;
	}

}
