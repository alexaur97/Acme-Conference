
package controllers.author;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Submission;
import forms.PaperForm;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;
	@Autowired
	private AuthorService		authorService;


	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int submissionId) {

		ModelAndView result;

		try {

			final Submission submission = this.submissionService.findOne(submissionId);
			Assert.isTrue(submission.getStatus().equals("ACCEPTED"));
			Assert.isTrue(submission.getAuthor().equals(this.authorService.findByPrincipal()));
			final PaperForm paperForm = new PaperForm();
			paperForm.setSubmissionId(submissionId);
			result = new ModelAndView("paper/edit");
			result.addObject("paperForm", paperForm);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/submission/author/list.do");

		}

		return result;
	}
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView accept(@Valid final PaperForm paperForm, final BindingResult binding) {

		ModelAndView result;
		final Submission submission = this.submissionService.reconstruction(paperForm);

		if (binding.hasErrors()) {
			result = new ModelAndView("paper/edit");
			result.addObject("paperForm", paperForm);
		} else
			try {
				this.submissionService.save(submission);
				result = new ModelAndView("redirect:/submission/author/list.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/#");

			}
		return result;
	}
}
