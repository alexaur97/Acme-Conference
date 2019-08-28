
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Submission;

@Controller
@RequestMapping("submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();

			final Collection<Submission> submissionsUnderReview = this.submissionService.findSubmissionUnderReview();
			final Collection<Submission> submissionsAccepted = this.submissionService.findSubmissionAccepted();
			final Collection<Submission> submissionsRejected = this.submissionService.findSubmissionRejected();

			result = new ModelAndView("submission/listAdm");
			result.addObject("requestURI", "/submission/administrator/list.do");
			result.addObject("submissionsUnderReview", submissionsUnderReview);
			result.addObject("submissionsAccepted", submissionsAccepted);
			result.addObject("submissionsRejected", submissionsRejected);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int submissionId) {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();
			final Submission submission = this.submissionService.findOne(submissionId);
			this.submissionService.assign(submission);

			result = new ModelAndView("redirect:/submission/administrator/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
