
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PresentationCommentService;
import services.TutorialCommentService;
import services.ConferenceCommentService;
import services.ConferenceService;
import services.PanelCommentService;
import controllers.AbstractController;

@Controller
@RequestMapping("/stats/administrator")
public class StatsAdministratorController extends AbstractController {

	@Autowired
	ConferenceService conferenceService;

	@Autowired
	ConferenceCommentService conferenceCommentService;

	@Autowired
	PresentationCommentService presentationCommentService;

	@Autowired
	PanelCommentService panelCommentService;
	
	@Autowired
	TutorialCommentService tutorialCommentService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		result = new ModelAndView("stats/display");

		try {
			final Collection<Double> conferencesPerCategory = this.conferenceService.statsConferencePerCategory();
			final Collection<Double> conferenceCommentsPerConference = this.conferenceCommentService
					.statsCommentsPerConference();
			final Collection<Double> commentsPerPresentation = this.presentationCommentService
					.statsCommentsPerPresentation();
			final Collection<Double> commentsPerPanel = this.panelCommentService.statsCommentsPerPanel();
			final Collection<Double> commentsPerTutorial = this.tutorialCommentService.statsCommentsPerTutorial();

			result.addObject("conferencesPerCategory", conferencesPerCategory);
			result.addObject("conferenceCommentsPerConference", conferenceCommentsPerConference);
			result.addObject("commentsPerPresentation", commentsPerPresentation);
			result.addObject("commentsPerPanel", commentsPerPanel);
			result.addObject("commentsPerTutorial", commentsPerTutorial);

			result.addObject("requestURI", "/stats/administrator/display.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");

		}
		return result;
	}
}
