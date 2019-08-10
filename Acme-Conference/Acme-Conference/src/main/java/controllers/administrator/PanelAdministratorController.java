
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
import services.PanelService;
import controllers.AbstractController;
import domain.Conference;
import domain.Panel;

@Controller
@RequestMapping("/panel/administrator")
public class PanelAdministratorController extends AbstractController {

	@Autowired
	private PanelService			panelService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int conferenceId) {
		ModelAndView result;
		try {

			this.administratorService.findByPrincipal();
			final Collection<Panel> panels = this.panelService.findByConference(conferenceId);

			result = new ModelAndView("panel/list");
			result.addObject("requestURI", "panel/list.do");
			result.addObject("panels", panels);

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
			final Panel panel = this.panelService.create();
			result = new ModelAndView("panel/edit");
			result.addObject("panel", panel);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findConference();
			if (conferences.isEmpty()) {
				result = new ModelAndView("panel/edit");
				result.addObject("message", "panel.conferences.error");
			}
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int panelId) {

		ModelAndView result;

		try {
			this.administratorService.findByPrincipal();
			final Collection<Conference> conferences = this.conferenceService.findConference();
			final Panel panel = this.panelService.findOne(panelId);
			result = new ModelAndView("panel/edit");
			result.addObject("panel", panel);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final Panel panel, final BindingResult binding) {

		ModelAndView result;
		this.administratorService.findByPrincipal();
		final Panel panelF = this.panelService.reconstruct(panel, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("panel/edit");
			final Collection<Conference> conferences = this.conferenceService.findConference();
			result.addObject("panel", panel);
			result.addObject("conferences", conferences);

		} else
			try {
				//Nos aseguramos que la fecha tiene que estar dentro de la fecha de la conferencia
				Assert.isTrue(panelF.getConference().getStartDate().before(panelF.getStartMoment()));
				Assert.isTrue(panelF.getStartMoment().before(panelF.getConference().getEndDate()));
				this.panelService.save(panelF);
				result = new ModelAndView("redirect:/conference/list.do");

			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findConference();
				final Boolean fechaPosterior = !(panelF.getConference().getStartDate().before(panelF.getStartMoment()));
				final Boolean fechaAnterior = !(panelF.getStartMoment().before(panelF.getConference().getEndDate()));
				if (fechaPosterior || fechaAnterior) {
					result = new ModelAndView("panel/edit");
					result.addObject("panel", panel);
					result.addObject("conferences", conferences);
					result.addObject("message", "panel.date.error");
				} else
					result = new ModelAndView("redirect:/#");
			}
		return result;

	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Panel panel, final BindingResult binding) {
		ModelAndView result;
		final Panel res = this.panelService.findOne(panel.getId());

		try {
			this.administratorService.findByPrincipal();
			this.panelService.delete(res);
			result = new ModelAndView("redirect:/conference/list.do");
		} catch (final Throwable oops) {
			final Collection<Conference> conferences = this.conferenceService.findConference();
			result = new ModelAndView("panel/edit");
			result.addObject("panel", panel);
			result.addObject("conferences", conferences);
			result.addObject("message", oops.getMessage());
		}
		return result;
	}

}