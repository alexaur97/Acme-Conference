
package controllers.reviewer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.ReviewerService;
import controllers.AbstractController;
import domain.Report;
import domain.Reviewer;

@Controller
@RequestMapping("/report/reviewer")
public class ReportReviewerController extends AbstractController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private ReviewerService reviewerService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Reviewer reviewer = this.reviewerService.findByPrincipal();
			Boolean hasSubmission = false;
			if (reviewer.getSubmission() != null)
				hasSubmission = true;
			final Collection<Report> reports = this.reportService.findReportsByPrincipal();
			result = new ModelAndView("report/list");
			result.addObject("requestURI", "/report/reviewer/list.do");
			result.addObject("reports", reports);
			result.addObject("hasSubmission", hasSubmission);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Reviewer reviewer = this.reviewerService.findByPrincipal();
			final Report report = this.reportService.create();
			result = this.createModelAndView(report);
			result.addObject("reviewer", reviewer);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(final Report report, final BindingResult binding) {

		ModelAndView result;

		try {

			final Reviewer reviewer = this.reviewerService.findByPrincipal();
			Assert.notNull(reviewer.getSubmission());

			final Report reportF = this.reportService.reconstruct(report, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(reportF);
				result.addObject("reviewer",reviewer);
				}	else
				try {
					Assert.notNull(reportF.getReviewer().getSubmission());

					this.reviewerService.refuseSubmission();
					this.reportService.save(reportF);
					result = new ModelAndView("redirect:/report/reviewer/list.do");

				} catch (final Throwable oops) {

					result = this.createModelAndView(report, "report.commit.error");
					result.addObject("reviewer",reviewer);
				}
		} catch (final Throwable oops) {
			final Reviewer reviewer = this.reviewerService.findByPrincipal();
			result = this.createModelAndView(report, "report.submission.error");
			result.addObject("reviewer",reviewer);

		}
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int reportId) {
		ModelAndView result;
		try {
			final Reviewer reviewer = this.reviewerService.findByPrincipal();
			final Report report = this.reportService.findOne(reportId);
			Assert.notNull(report);
			Assert.isTrue(report.getReviewer().equals(reviewer));
			result = new ModelAndView("report/show");
			result.addObject("requestURI", "report/reviewer/show.do");
			result.addObject("report", report);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	protected ModelAndView createModelAndView(final Report report) {
		return this.createModelAndView(report, null);
	}

	protected ModelAndView createModelAndView(final Report report, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("report/create");
		result.addObject("report", report);
		result.addObject("message", messageCode);
		return result;
	}

}
