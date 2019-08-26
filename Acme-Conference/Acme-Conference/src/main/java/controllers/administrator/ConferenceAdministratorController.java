
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Category;
import domain.Conference;
import domain.Submission;

@Controller
@RequestMapping("conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private CategoryService			categoryService;


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
			result.addObject("requestURI", "/conference/administrator/list.do");
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

			final Collection<Submission> allSubmissions = this.submissionService.findSubmissionsByConference(conference);
			final Date date = new Date();
			final Boolean submissions = !allSubmissions.isEmpty() && conference.getSubmissionDeadline().before(date);
			result.addObject("submissions", submissions);

			final Collection<Submission> acceptedSubmissions = this.submissionService.findAcceptedSubmissionsByConference(conference);
			final Collection<Submission> rejectedSubmissions = this.submissionService.findRejectedSubmissionsByConference(conference);

			final Boolean bool = acceptedSubmissions.size() + rejectedSubmissions.size() > 0;

			result.addObject("requestURI", "conference/administrator/show.do");
			result.addObject("conference", conference);
			result.addObject("acceptedSubmissions", acceptedSubmissions);
			result.addObject("rejectedSubmissions", rejectedSubmissions);
			result.addObject("bool", bool);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/decision", method = RequestMethod.GET)
	public ModelAndView decisionProcedure(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();
			final Conference conference = this.conferenceService.findOne(conferenceId);
			this.conferenceService.decisionProcedure(conference);

			result = new ModelAndView("redirect:/conference/administrator/show.do?conferenceId=" + conferenceId);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			final Conference conference = this.conferenceService.create();

			result = this.createEditModelAndView(conference);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("conferenceId") final int conferenceId) {
		ModelAndView result;

		try {

			final Conference conference = this.conferenceService.findOne(conferenceId);

			Assert.notNull(conference);
			result = this.createEditModelAndView(conference);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("conference") Conference conference, final BindingResult binding) {
		ModelAndView res;
		try {

			conference = this.conferenceService.reconstruct(conference, binding);

			if (binding.hasErrors())
				res = this.createEditModelAndView(conference);
			else
				try {
					this.conferenceService.save(conference);
					res = new ModelAndView("redirect:/conference/list.do");

				} catch (final Throwable oops) {
					if (conference.getSubmissionDeadline().before(new Date()))
						res = this.createEditModelAndView(conference, "conference.commmit.error.dates");
					else if (conference.getSubmissionDeadline().after(conference.getNotification()))
						res = this.createEditModelAndView(conference, "conference.commit.errorDN");
					else if (conference.getNotification().after(conference.getCameraReady()))
						res = this.createEditModelAndView(conference, "conference.commit.errorNC");
					else if (conference.getCameraReady().after(conference.getStartDate()))
						res = this.createEditModelAndView(conference, "conference.commit.errorCS");
					else if (conference.getStartDate().after(conference.getEndDate()))
						res = this.createEditModelAndView(conference, "conference.commit.errorSE");
					else
						res = this.createEditModelAndView(conference, "conference.commit.error");

				}
		} catch (final Throwable oops) {

			res = this.createEditModelAndView(conference, "conference.commit.error");

		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Conference conference) {
		return this.createEditModelAndView(conference, null);
	}
	protected ModelAndView createEditModelAndView(final Conference conference, final String messageCode) {
		this.administratorService.findByPrincipal();
		final ModelAndView res;

		final Collection<Category> categories = this.categoryService.findAll();
		res = new ModelAndView("conference/edit");
		res.addObject("conference", conference);
		res.addObject("categories", categories);
		res.addObject("message", messageCode);

		return res;
	}
}
