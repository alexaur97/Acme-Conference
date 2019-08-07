
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {

			final Collection<Conference> conferences = this.conferenceService.findAll();

			result = new ModelAndView("conference/list");
			result.addObject("requestURI", "conference/list.do");
			result.addObject("conferences", conferences);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchForm() {
		ModelAndView result;
		final Conference conference = new Conference();
		try {
			result = new ModelAndView("conference/search");
			result.addObject("conference", conference);
			result.addObject("requestURI", "conference/search.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchList(final Conference conference, final BindingResult binding) {
		ModelAndView result;
		try {
			result = new ModelAndView("conference/search");
			final Collection<Conference> conferences = this.conferenceService.searchConference(conference.getTitle());
			result.addObject("conferences", conferences);
			result.addObject("requestURI", "conference/search.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

}
