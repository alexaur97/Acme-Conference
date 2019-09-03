
package controllers.all;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TutorialCommentService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Tutorial;
import domain.TutorialComment;

@Controller
@RequestMapping("/tutorial/comment")
public class TutorialCommentController extends AbstractController {

	@Autowired
	private TutorialCommentService	tutorialCommentService;

	@Autowired
	private TutorialService			tutorialService;

	@Autowired
	private Validator				validator;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.notNull(tutorial);
			final TutorialComment tutorialComment = this.tutorialCommentService.create();
			result = new ModelAndView("tutorialComment/create");
			result.addObject("tutorialComment", tutorialComment);
			result.addObject("tutorialId", tutorialId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int commentId) {
		ModelAndView result;
		try {
			final TutorialComment tutorialComment = this.tutorialCommentService.findOne(commentId);
			result = new ModelAndView("tutorialComment/show");
			result.addObject("tutorialComment", tutorialComment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam final int tutorialId, @ModelAttribute("tutorialComment") final TutorialComment tutorialComment, final BindingResult binding) {
		ModelAndView result;

		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		tutorialComment.setTutorial(tutorial);
		final Date date = new Date();
		tutorialComment.setMoment(date);

		this.validator.validate(tutorialComment, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("tutorialComment/create");
			result.addObject("tutorialComment", tutorialComment);
			result.addObject("tutorialId", tutorialId);
		} else
			try {
				final TutorialComment saved = this.tutorialCommentService.save(tutorialComment);
				result = new ModelAndView("redirect:/tutorial/comment/show.do?commentId=" + saved.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("tutorialComment/create");
				result.addObject("tutorialComment", tutorialComment);
				result.addObject("message", "tutorialComment.commit.error");
				result.addObject("tutorialId", tutorialId);
			}
		return result;

	}

}
