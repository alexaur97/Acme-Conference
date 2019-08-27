
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceCommentService;
import services.ConferenceService;
import services.PanelCommentService;
import services.PresentationCommentService;
import services.RegistrationService;
import services.SubmissionService;
import services.TutorialCommentService;
import controllers.AbstractController;

@Controller
@RequestMapping("/stats/administrator")
public class StatsAdministratorController extends AbstractController {

	@Autowired
	ConferenceService			conferenceService;

	@Autowired
	ConferenceCommentService	conferenceCommentService;

	@Autowired
	PresentationCommentService	presentationCommentService;

	@Autowired
	PanelCommentService			panelCommentService;

	@Autowired
	TutorialCommentService		tutorialCommentService;

	@Autowired
	SubmissionService			submissionService;

	@Autowired
	RegistrationService			registrationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		result = new ModelAndView("stats/display");

		try {
			final Collection<Double> conferencesPerDays = this.conferenceService.statsConferencesDays();
			final Collection<Double> conferencesPerCategory = this.conferenceService.statsConferencePerCategory();
			final Collection<Double> conferenceCommentsPerConference = this.conferenceCommentService.statsCommentsPerConference();
			final Collection<Double> commentsPerPresentation = this.presentationCommentService.statsCommentsPerPresentation();
			final Collection<Double> commentsPerPanel = this.panelCommentService.statsCommentsPerPanel();
			final Collection<Double> commentsPerTutorial = this.tutorialCommentService.statsCommentsPerTutorial();
			final Collection<Double> submissionsPerConference = this.submissionService.statsSubmissionsPerConference();
			final Collection<Double> registrationsPerConference = this.registrationService.statsRegistrationsPerConference();
			final Collection<Double> conferencesFee = this.conferenceService.statsConferencesFee();

			result.addObject("conferencesPerDays", conferencesPerDays);
			result.addObject("conferencesPerCategory", conferencesPerCategory);
			result.addObject("conferenceCommentsPerConference", conferenceCommentsPerConference);
			result.addObject("commentsPerPresentation", commentsPerPresentation);
			result.addObject("commentsPerPanel", commentsPerPanel);
			result.addObject("commentsPerTutorial", commentsPerTutorial);
			result.addObject("submissionsPerConference", submissionsPerConference);
			result.addObject("registrationsPerConference", registrationsPerConference);
			result.addObject("conferencesFee", conferencesFee);

			result.addObject("requestURI", "/stats/administrator/display.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");

		}
		return result;
	}
}
