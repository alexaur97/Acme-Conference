
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
			result.addObject("requestURI", "presentation/administrator/list.do");
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
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			Assert.notEmpty(conferences);
			final Presentation presentation = this.presentationService.create();
			result = new ModelAndView("presentation/edit");
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
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
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
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
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);

		} else
			try {
				//Nos aseguramos que la fecha tiene que estar dentro de la fecha de la conferencia
				Assert.isTrue(presentationF.getStartMoment().before(presentationF.getConference().getEndDate()));
				Assert.isTrue(!(presentationF.getStartMoment().before(presentationF.getConference().getStartDate())));
				Assert.isTrue(Utils.validateURL(presentationF.getAttachments()));
				Assert.isTrue(presentationF.getendMoment().before(presentationF.getConference().getEndDate()));
				Assert.isTrue(presentationF.getConference().getMode().equals("FINAL"));
				Assert.isTrue(presentationF.getendMoment().after(presentationF.getStartMoment()));
				final Presentation saved = this.presentationService.save(presentationF);
				result = new ModelAndView("redirect:/conference/activity/presentation/show.do?presentationId=" + saved.getId());

			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findNextConferences();
				final Boolean fechaPosterior = presentationF.getStartMoment().before(presentationF.getConference().getStartDate());
				final Boolean fechaAnterior = !(presentationF.getStartMoment().before(presentationF.getConference().getEndDate()));
				final Boolean urlInvalida = Utils.validateURL(presentationF.getAttachments());

				final Boolean fechaFinAnterior = presentationF.getendMoment().before(presentationF.getStartMoment());
				final Boolean fechaFinPosterior = presentationF.getendMoment().after(presentationF.getConference().getEndDate());

				if (fechaPosterior || fechaAnterior) {
					result = new ModelAndView("presentation/edit");
					result.addObject("presentation", presentation);
					result.addObject("conferences", conferences);
					result.addObject("message", "presentation.date.error");
				} else if (urlInvalida.equals(false)) {
					result = new ModelAndView("presentation/edit");
					result.addObject("presentation", presentation);
					result.addObject("conferences", conferences);
					result.addObject("message", "presentation.attachments.error");

				} else if (fechaFinPosterior) {
					result = new ModelAndView("presentation/edit");
					result.addObject("presentation", presentation);
					result.addObject("conferences", conferences);
					result.addObject("message", "presentation.endMoment.error");
				} else if (fechaFinAnterior) {
					result = new ModelAndView("presentation/edit");
					result.addObject("presentation", presentation);
					result.addObject("conferences", conferences);
					result.addObject("message", "presentation.endMomentBefore.error");

				} else {
					result = new ModelAndView("presentation/edit");
					result.addObject("presentation", presentation);
					result.addObject("conferences", conferences);
					result.addObject("message", "presentation.commit.error");
				}
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
			final Collection<Conference> conferences = this.conferenceService.findNextConferences();
			result = new ModelAndView("presentation/edit");
			result.addObject("presentation", presentation);
			result.addObject("conferences", conferences);
			result.addObject("message", oops.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int presentationId) {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();
			final Presentation presentation = this.presentationService.findOne(presentationId);
			Assert.notNull(presentation);

			result = new ModelAndView("presentation/show");

			final Integer duracionSegundos = (int) ((presentation.getendMoment().getTime() - presentation.getStartMoment().getTime()) / 1000);
			final Integer endMoment = duracionSegundos / 60;

			result.addObject("requestURI", "presentation/administrator/show.do");
			result.addObject("presentation", presentation);
			result.addObject("endMoment", endMoment);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
