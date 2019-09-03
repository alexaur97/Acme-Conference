
package controllers.administrator;

import java.util.Collection;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConferenceService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/administrator")
public class TutorialAdministratorController extends AbstractController {

	@Autowired
	private TutorialService			tutorialService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private SectionService			sectionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int conferenceId) {
		ModelAndView result;
		try {

			this.administratorService.findByPrincipal();
			final Conference conference = this.conferenceService.findOne(conferenceId);
			Assert.notNull(conference);
			final Collection<Tutorial> tutorials = this.tutorialService.findByConference(conferenceId);

			result = new ModelAndView("tutorial/list");
			result.addObject("requestURI", "tutorial/administrator/list.do");
			result.addObject("tutorials", tutorials);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			Assert.notEmpty(conferences);
			final Tutorial tutorial = this.tutorialService.create();
			result = new ModelAndView("tutorial/edit");
			result.addObject("tutorial", tutorial);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			if (conferences.isEmpty()) {
				result = new ModelAndView("tutorial/edit");
				result.addObject("message", "tutorial.conferences.error");
			}
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {

		ModelAndView result;

		try {
			this.administratorService.findByPrincipal();
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.notNull(tutorial);
			result = new ModelAndView("tutorial/edit");
			result.addObject("tutorial", tutorial);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		this.administratorService.findByPrincipal();
		final Tutorial tutorialF = this.tutorialService.reconstruct(tutorial, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("tutorial/edit");
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			result.addObject("tutorial", tutorial);
			result.addObject("conferences", conferences);

		} else
			try {
				// Nos aseguramos que la fecha tiene que estar dentro de la fecha de la
				// conferencia
				Assert.isTrue(tutorialF.getStartMoment().before(tutorialF.getConference().getEndDate()));
				Assert.isTrue(!(tutorialF.getStartMoment().before(tutorialF.getConference().getStartDate())));
				Assert.isTrue(Utils.validateURL(tutorialF.getAttachments()));
				// Assert.isTrue(tutorialF.getStartMoment().before(tutorialF.getendMoment()));
				Assert.isTrue(tutorialF.getendMoment().before(tutorialF.getConference().getEndDate()));
				Assert.isTrue(tutorialF.getConference().getMode().equals("FINAL"));
				Assert.isTrue(tutorialF.getendMoment().after(tutorialF.getStartMoment()));
				final Tutorial saved = this.tutorialService.save(tutorialF);
				result = new ModelAndView("redirect:/conference/activity/tutorial/show.do?tutorialId=" + saved.getId());

			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findNextConferences();
				final Boolean fechaPosterior = tutorialF.getStartMoment().before(tutorialF.getConference().getStartDate());
				final Boolean fechaAnterior = !(tutorialF.getStartMoment().before(tutorialF.getConference().getEndDate()));
				final Boolean fechaFinAnterior = tutorialF.getendMoment().before(tutorialF.getStartMoment());
				final Boolean fechaFinPosterior = tutorialF.getendMoment().after(tutorialF.getConference().getEndDate());
				final Boolean urlInvalida = Utils.validateURL(tutorialF.getAttachments());
				// final Boolean fechaEndMoment =
				// tutorialF.getendMoment().before(tutorialF.getStartMoment());
				if (fechaPosterior || fechaAnterior) {
					result = new ModelAndView("tutorial/edit");
					result.addObject("tutorial", tutorial);
					result.addObject("conferences", conferences);
					result.addObject("message", "tutorial.date.error");

				} else if (urlInvalida.equals(false)) {
					result = new ModelAndView("tutorial/edit");
					result.addObject("tutorial", tutorial);
					result.addObject("conferences", conferences);
					result.addObject("message", "tutorial.attachments.error");

				} else if (fechaFinPosterior) {
					result = new ModelAndView("tutorial/edit");
					result.addObject("tutorial", tutorial);
					result.addObject("conferences", conferences);
					result.addObject("message", "tutorial.endMoment.error");
				} else if (fechaFinAnterior) {
					result = new ModelAndView("tutorial/edit");
					result.addObject("tutorial", tutorial);
					result.addObject("conferences", conferences);
					result.addObject("message", "tutorial.endMomentBefore.error");

				} else {
					result = new ModelAndView("tutorial/edit");
					result.addObject("tutorial", tutorial);
					result.addObject("conferences", conferences);
					result.addObject("message", "tutorial.commit.error");
				}
			}
		return result;

	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		final Tutorial res = this.tutorialService.findOne(tutorial.getId());

		try {
			this.administratorService.findByPrincipal();
			final Collection<Section> sections = this.sectionService.findByTutorial(tutorial.getId());
			for (final Section s : sections)
				this.sectionService.delete(s);
			this.tutorialService.delete(res);
			result = new ModelAndView("redirect:/conference/list.do");
		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			result = new ModelAndView("tutorial/edit");
			result.addObject("tutorial", tutorial);
			result.addObject("conferences", conferences);
			result.addObject("message", oops.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		ModelAndView result;
		try {
			final Collection<Section> sections = this.sectionService.findByTutorial(tutorialId);
			this.administratorService.findByPrincipal();
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.notNull(tutorial);
			final Integer duracionSegundos = (int) ((tutorial.getendMoment().getTime() - tutorial.getStartMoment().getTime()) / 1000);
			final Integer endMoment = duracionSegundos / 60;

			result = new ModelAndView("tutorial/show");
			result.addObject("requestURI", "tutorial/administrator/show.do");
			result.addObject("tutorial", tutorial);
			result.addObject("sections", sections);
			result.addObject("endMoment", endMoment);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
