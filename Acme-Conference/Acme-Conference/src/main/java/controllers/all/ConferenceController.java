
package controllers.all;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Conference;
import domain.Submission;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;
	@Autowired
	private SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {

			//Solo aparecen las conferencias que no han pasado
			final Collection<Conference> conferences = this.conferenceService.findConference();

			final Collection<Conference> pastConferences = this.conferenceService.findPastConference();
			final Collection<Conference> runningConferences = this.conferenceService.findRunningConference();

			result = new ModelAndView("conference/list");
			result.addObject("requestURI", "conference/list.do");
			result.addObject("conferences", conferences);
			result.addObject("pastConferences", pastConferences);
			result.addObject("runningConferences", runningConferences);

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
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			final Conference conference = this.conferenceService.findOne(conferenceId);
			result = new ModelAndView("conference/show");

			final Collection<Submission> allSubmissions = this.submissionService.findSubmissionsByConference(conference);
			final Date date = new Date();
			final Boolean submissions = !allSubmissions.isEmpty() && conference.getSubmissionDeadline().before(date);
			result.addObject("submissions", submissions);

			final Collection<Submission> acceptedSubmissions = this.submissionService.findAcceptedSubmissionsByConference(conference);
			final Collection<Submission> rejectedSubmissions = this.submissionService.findRejectedSubmissionsByConference(conference);

			final Boolean bool = acceptedSubmissions.size() + rejectedSubmissions.size() > 0;

			result.addObject("requestURI", "conference/show.do");
			result.addObject("conference", conference);
			result.addObject("acceptedSubmissions", acceptedSubmissions);
			result.addObject("rejectedSubmissions", rejectedSubmissions);
			result.addObject("bool", bool);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
