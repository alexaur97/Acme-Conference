
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

import services.PresentationCommentService;
import services.PresentationService;
import controllers.AbstractController;
import domain.Presentation;
import domain.PresentationComment;

@Controller
@RequestMapping("/presentation/comment")
public class PresentationCommentController extends AbstractController {

	@Autowired
	private PresentationCommentService	presentationCommentService;

	@Autowired
	private PresentationService			presentationService;

	@Autowired
	private Validator					validator;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int presentationId) {
		ModelAndView result;
		try {
			final Presentation presentation = this.presentationService.findOne(presentationId);
			Assert.notNull(presentation);
			final PresentationComment presentationComment = this.presentationCommentService.create();
			result = new ModelAndView("presentationComment/create");
			result.addObject("presentationComment", presentationComment);
			result.addObject("presentationId", presentationId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int commentId) {
		ModelAndView result;
		try {
			final PresentationComment presentationComment = this.presentationCommentService.findOne(commentId);
			result = new ModelAndView("presentationComment/show");
			result.addObject("presentationComment", presentationComment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam final int presentationId, @ModelAttribute("presentationComment") final PresentationComment presentationComment, final BindingResult binding) {
		ModelAndView result;

		final Presentation presentation = this.presentationService.findOne(presentationId);
		presentationComment.setPresentation(presentation);
		final Date date = new Date();
		presentationComment.setMoment(date);

		this.validator.validate(presentationComment, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("presentationComment/create");
			result.addObject("presentationComment", presentationComment);
			result.addObject("presentationId", presentationId);
		} else
			try {
				final PresentationComment saved = this.presentationCommentService.save(presentationComment);
				result = new ModelAndView("redirect:/presentation/comment/show.do?commentId=" + saved.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("presentationComment/create");
				result.addObject("presentationComment", presentationComment);
				result.addObject("message", "presentationComment.commit.error");
				result.addObject("presentationId", presentationId);
			}
		return result;

	}

}
