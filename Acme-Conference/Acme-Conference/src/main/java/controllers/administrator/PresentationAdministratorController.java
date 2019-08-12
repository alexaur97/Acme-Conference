
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
import services.PresentationService;
import controllers.AbstractController;
import domain.Conference;
import domain.Presentation;

@Controller
@RequestMapping("/presentation/administrator")
public class PresentationAdministratorController extends AbstractController {

	@Autowired
	private PresentationService		presentationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int conferenceId) {
		ModelAndView result;
		try {

			this.administratorService.findByPrincipal();
			final Collection<Presentation> presentations = this.presentationService.findByConference(conferenceId);

			result = new ModelAndView("presentation/list");
			result.addObject("requestURI", "presentation/list.do");
			result.addObject("presentations", presentations);

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
			final Presentation presentation = this.presentationService.create();
			result = new ModelAndView("presentation/edit");
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findConference();
			if (conferences.isEmpty()) {
				result = new ModelAndView("presentation/edit");
				result.addObject("message", "presentation.conferences.error");
			}
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int presentationId) {

		ModelAndView result;

		try {
			this.administratorService.findByPrincipal();
			final Collection<Conference> conferences = this.conferenceService.findConference();
			final Presentation presentation = this.presentationService.findOne(presentationId);
			Assert.notNull(presentation);
			result = new ModelAndView("presentation/edit");
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final Presentation presentation, final BindingResult binding) {

		ModelAndView result;
		this.administratorService.findByPrincipal();
		final Presentation presentationF = this.presentationService.reconstruct(presentation, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("presentation/edit");
			final Collection<Conference> conferences = this.conferenceService.findConference();
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);

		} else
			try {
				//Nos aseguramos que la fecha tiene que estar dentro de la fecha de la conferencia
				Assert.isTrue(presentationF.getConference().getStartDate().before(presentationF.getStartMoment()));
				Assert.isTrue(presentationF.getStartMoment().before(presentationF.getConference().getEndDate()));
				this.presentationService.save(presentationF);
				result = new ModelAndView("redirect:/conference/list.do");

			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findConference();
				final Boolean fechaPosterior = !(presentationF.getConference().getStartDate().before(presentationF.getStartMoment()));
				final Boolean fechaAnterior = !(presentationF.getStartMoment().before(presentationF.getConference().getEndDate()));
				if (fechaPosterior || fechaAnterior) {
					result = new ModelAndView("presentation/edit");
					result.addObject("presentation", presentation);
					result.addObject("conferences", conferences);
					result.addObject("message", "presentation.date.error");
				} else
					result = new ModelAndView("redirect:/#");
			}
		return result;

	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Presentation presentation, final BindingResult binding) {
		ModelAndView result;
		final Presentation res = this.presentationService.findOne(presentation.getId());

		try {
			this.administratorService.findByPrincipal();
			this.presentationService.delete(res);
			result = new ModelAndView("redirect:/conference/list.do");
		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findConference();
			result = new ModelAndView("presentation/edit");
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);
			result.addObject("message", oops.getMessage());
		}
		return result;
	}

}
