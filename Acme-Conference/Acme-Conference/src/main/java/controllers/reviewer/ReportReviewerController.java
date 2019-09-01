package controllers.reviewer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import domain.Panel;
import domain.Paper;

import domain.Report;
import domain.Submission;
import forms.MakeSubmissionForm;
import forms.PaperForm;
import miscellaneous.Utils;

@Controller
@RequestMapping("/report/reviewer")
public class ReportReviewerController extends AbstractController {

	@Autowired
	private SubmissionService submissionService;

	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Report> reports = this.reportService.findReportsByPrincipal();
			result = new ModelAndView("report/list");
			result.addObject("reports", reports);
			
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Report report = this.reportService.create();
			result = this.createModelAndView(report);
			
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params="save")
	public ModelAndView create(@ModelAttribute("report") Report report, BindingResult binding) {
		ModelAndView result;
		report = this.reportService.reconstruct(report, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(report);
		} else
			try {
				this.reportService.save(report);
				result = new ModelAndView("redirect: /report/referee/list.do");
			} catch (final Throwable oops) {
				this.createModelAndView(report, "report.commit.error");
				result = new ModelAndView("redirect:/#");
			}
		return result;
	}

	protected ModelAndView createModelAndView(Report report) {
		return createModelAndView(report, null);
	}

	protected ModelAndView createModelAndView(Report report, String messageCode) {
		ModelAndView result;
		result = new ModelAndView("report/create");
		result.addObject("report", report);
		result.addObject("message", messageCode);
		return result;
	}
	
}