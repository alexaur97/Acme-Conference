
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;

@Controller
@RequestMapping("conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();

			final Collection<Conference> conferencesSubmission = this.conferenceService.findSubmissionLastFiveDays();
			final Collection<Conference> conferencesNotification = this.conferenceService.findNotificationLessFiveDays();
			final Collection<Conference> conferencesCameraReady = this.conferenceService.findCameraReadyLessFiveDays();
			final Collection<Conference> conferencesStartDate = this.conferenceService.findStartDateLessFiveDays();

			result = new ModelAndView("conference/listAdm");
			result.addObject("requestURI", "conference/administrator/list.do");
			result.addObject("conferencesSubmission", conferencesSubmission);
			result.addObject("conferencesNotification", conferencesNotification);
			result.addObject("conferencesCameraReady", conferencesCameraReady);
			result.addObject("conferencesStartDate", conferencesStartDate);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();
			final Conference conference = this.conferenceService.findOne(conferenceId);
			result = new ModelAndView("conference/show");
			result.addObject("requestURI", "conference/administrator/show.do");
			result.addObject("conference", conference);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
