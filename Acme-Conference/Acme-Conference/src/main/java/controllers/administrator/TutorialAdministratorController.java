
package controllers.administrator;

import java.util.Collection;

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
			final Collection<Tutorial> tutorials = this.tutorialService.findByConference(conferenceId);

			result = new ModelAndView("tutorial/list");
			result.addObject("requestURI", "tutorial/list.do");
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
			final Collection<Conference> conferences = this.conferenceService.findConference();
			Assert.notEmpty(conferences);
			final Tutorial tutorial = this.tutorialService.create();
			result = new ModelAndView("tutorial/edit");
			result.addObject("tutorial", tutorial);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findConference();
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
			final Collection<Conference> conferences = this.conferenceService.findConference();
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
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
			final Collection<Conference> conferences = this.conferenceService.findConference();
			result.addObject("tutorial", tutorial);
			result.addObject("conferences", conferences);

		} else
			try {
				//Nos aseguramos que la fecha tiene que estar dentro de la fecha de la conferencia
				Assert.isTrue(tutorialF.getConference().getStartDate().before(tutorialF.getStartMoment()));
				Assert.isTrue(tutorialF.getStartMoment().before(tutorialF.getConference().getEndDate()));
				this.tutorialService.save(tutorialF);
				result = new ModelAndView("redirect:/conference/administrator/list.do");

			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findConference();
				final Boolean fechaPosterior = !(tutorialF.getConference().getStartDate().before(tutorialF.getStartMoment()));
				final Boolean fechaAnterior = !(tutorialF.getStartMoment().before(tutorialF.getConference().getEndDate()));
				if (fechaPosterior || fechaAnterior) {
					result = new ModelAndView("tutorial/edit");
					result.addObject("tutorial", tutorial);
					result.addObject("conferences", conferences);
					result.addObject("message", "tutorial.date.error");
				} else
					result = new ModelAndView("redirect:/#");
			}
		return result;

	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		final Tutorial res = this.tutorialService.findOne(tutorial.getId());

		try {
			this.administratorService.findByPrincipal();
			this.tutorialService.delete(res);
			result = new ModelAndView("redirect:/conference/administrator/list.do");
		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findConference();
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
			result = new ModelAndView("tutorial/show");
			result.addObject("requestURI", "tutorial/administrator/show.do");
			result.addObject("tutorial", tutorial);
			result.addObject("sections", sections);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
