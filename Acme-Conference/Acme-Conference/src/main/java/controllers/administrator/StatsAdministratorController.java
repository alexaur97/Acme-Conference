
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityCommentService;
import services.ConferenceCommentService;
import services.ConferenceService;
import controllers.AbstractController;

@Controller
@RequestMapping("/stats/administrator")
public class StatsAdministratorController extends AbstractController {

	@Autowired
	ConferenceService			conferenceService;

	@Autowired
	ConferenceCommentService	conferenceCommentService;

	@Autowired
	ActivityCommentService		activityCommentService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		result = new ModelAndView("stats/display");

		try {
			final Collection<Double> conferencesPerCategory = this.conferenceService.statsConferencePerCategory();
			final Collection<Double> conferenceCommentsPerConference = this.conferenceCommentService.statsCommentsPerConference();
			final Collection<Double> activityCommentsPerActivity = this.activityCommentService.statsCommentsPerActivity();

			result.addObject("conferencesPerCategory", conferencesPerCategory);
			result.addObject("conferenceCommentsPerConference", conferenceCommentsPerConference);
			result.addObject("activityCommentsPerActivity", activityCommentsPerActivity);

			result.addObject("requestURI", "/stats/administrator/display.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");

		}
		return result;
	}
}
