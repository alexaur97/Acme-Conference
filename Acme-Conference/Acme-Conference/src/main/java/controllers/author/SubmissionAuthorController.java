
package controllers.author;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuthorService;
import services.ConferenceService;
import services.PaperService;
import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Conference;
import domain.Paper;
import domain.Report;
import domain.Submission;
import forms.MakeSubmissionForm;
import forms.PaperForm;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ReportService		reportService;
	@Autowired
	private PaperService		paperService;

	@Autowired
	private ReviewerService		reviewerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Submission> submissions = this.submissionService.findMySubmissions();
			result = new ModelAndView("submission/list");
			result.addObject("requestURI", "submission/author/list.do");
			result.addObject("submissions", submissions);
			final String a = "ACCEPTED";
			result.addObject("a", a);
			
			final Date date = new Date();
			result.addObject("date", date);

			
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		ModelAndView result;
		try {
			final Submission submission = this.submissionService.findOne(submissionId);
			Assert.isTrue(this.actorService.findByPrincipal().getId() == submission.getAuthor().getId());
			Assert.notNull(submission);
			result = new ModelAndView("submission/show");
			result.addObject("requestURI", "submission/author/show.do");
			result.addObject("submission", submission);

			final Collection<Report> reports = this.reportService.findReportsInAcceptedSubmission(submission);
			result.addObject("reports", reports);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		try {
			final MakeSubmissionForm makeSubmissionForm = new MakeSubmissionForm();
			final Collection<Conference> conferences = this.conferenceService.findOpenConferences();
			res = new ModelAndView("submission/edit");
			res.addObject("makeSubmissionForm", makeSubmissionForm);
			res.addObject("conferences", conferences);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final MakeSubmissionForm makeSubmissionForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			final Collection<Conference> conferences = this.conferenceService.findOpenConferences();
			res = new ModelAndView("submission/edit");
			res.addObject("conferences", conferences);
			res.addObject("makeSubmissionForm", makeSubmissionForm);
		} else
			try {
				res = new ModelAndView("submission/edit");
				final Submission s = this.submissionService.reconstructSubmission(makeSubmissionForm);
				this.submissionService.save(s);

				res = new ModelAndView("redirect:/submission/author/list.do");
			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findOpenConferences();
				res = new ModelAndView("submission/edit");
				res.addObject("conferences", conferences);
				res.addObject("makeSubmissionForm", makeSubmissionForm);
			}

		return res;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int submissionId) {

		ModelAndView result;

		try {

			final Submission submission = this.submissionService.findOne(submissionId);
			final Date limite = submission.getConference().getCameraReady();
			final Date date = new Date();
			Assert.isNull(submission.getCameraReady());
			Assert.isTrue(limite.after(date));
			Assert.isTrue(submission.getStatus().equals("ACCEPTED"));
			Assert.isTrue(submission.getAuthor().equals(this.authorService.findByPrincipal()));

			final PaperForm paperForm = new PaperForm();
			paperForm.setSubmissionId(submissionId);
			result = new ModelAndView("paper/edit");
			result.addObject("paperForm", paperForm);
			result.addObject("date", date);

		} catch (final Throwable oops) {
			final Submission s = this.submissionService.findOne(submissionId);
			final Date limite = s.getConference().getSubmissionDeadline();
			final Date date = new Date();
			if (!limite.after(date)) {
				final Collection<Submission> submissions = this.submissionService.findMySubmissions();
				result = new ModelAndView("submission/list");
				result.addObject("requestURI", "submission/author/list.do");
				result.addObject("submissions", submissions);
				final String a = "ACCEPTED";
				result.addObject("a", a);
				result.addObject("errorLimite", true);
			} else
				result = new ModelAndView("redirect:/submission/author/list.do");

		}

		return result;
	}
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView accept(@Valid final PaperForm paperForm, final BindingResult binding) {

		ModelAndView result;
		final Paper paper = this.paperService.reconstructionSub(paperForm, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("paper/edit");
			result.addObject("paperForm", paperForm);
		} else
			try {

				final Submission submission = this.submissionService.reconstruction(paperForm.getSubmissionId(), paper);
				this.submissionService.save(submission);
				result = new ModelAndView("redirect:/submission/author/list.do");

			} catch (final Throwable oops) {

				result = new ModelAndView("redirect:/#");
			}
		return result;
	}
}
