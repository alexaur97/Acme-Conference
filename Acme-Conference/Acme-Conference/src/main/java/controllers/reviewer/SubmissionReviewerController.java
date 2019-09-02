
package controllers.reviewer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/reviewer")
public class SubmissionReviewerController extends AbstractController {

	@Autowired
	SubmissionService	submissionService;

	@Autowired
	ReviewerService		reviewerService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		ModelAndView result;
		try {
			final Reviewer r = this.reviewerService.findByPrincipal();
			final Submission submission = this.submissionService.findOne(submissionId);
			Assert.notNull(submission);
			final Collection<Submission> submissions = this.submissionService.findFromReportReviewer(r.getId());
			Assert.isTrue(submissions.contains(submission));
			result = new ModelAndView("submission/showReviewer");
			result.addObject("requestURI", "submission/reviewer/show.do");
			result.addObject("submission", submission);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
