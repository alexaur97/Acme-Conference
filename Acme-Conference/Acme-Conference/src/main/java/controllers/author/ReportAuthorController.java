package controllers.author;

import java.util.Collection;

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
import services.SubmissionService;
import controllers.AbstractController;
import domain.Conference;

import domain.Paper;

import domain.Report;
import domain.Submission;
import forms.MakeSubmissionForm;
import forms.PaperForm;

@Controller
@RequestMapping("/report/author")
public class ReportAuthorController extends AbstractController {

	@Autowired
	private SubmissionService submissionService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private ReportService reportService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int reportId) {
		ModelAndView result;
		try {
			final Report report = this.reportService.findOne(reportId);
			Assert.isTrue(this.actorService.findByPrincipal().getId() == report.getSubmission().getAuthor().getId());
			Assert.notNull(report);
			result = new ModelAndView("report/show");
			result.addObject("submission", report);
			Submission submission = report.getSubmission();
			Collection<Report> reports = this.reportService.findReportsInAcceptedSubmission(submission);
			Assert.isTrue(reports.contains(report));
			result.addObject("report", report);
				
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}