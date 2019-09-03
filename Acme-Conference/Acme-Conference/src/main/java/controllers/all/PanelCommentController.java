
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

import services.PanelCommentService;
import services.PanelService;
import controllers.AbstractController;
import domain.Panel;
import domain.PanelComment;

@Controller
@RequestMapping("/panel/comment")
public class PanelCommentController extends AbstractController {

	@Autowired
	private PanelCommentService	panelCommentService;

	@Autowired
	private PanelService		panelService;

	@Autowired
	private Validator			validator;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int panelId) {
		ModelAndView result;
		try {
			final Panel panel = this.panelService.findOne(panelId);
			Assert.notNull(panel);
			final PanelComment panelComment = this.panelCommentService.create();
			result = new ModelAndView("panelComment/create");
			result.addObject("panelComment", panelComment);
			result.addObject("panelId", panelId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int commentId) {
		ModelAndView result;
		try {
			final PanelComment panelComment = this.panelCommentService.findOne(commentId);
			result = new ModelAndView("panelComment/show");
			result.addObject("panelComment", panelComment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam final int panelId, @ModelAttribute("panelComment") final PanelComment panelComment, final BindingResult binding) {
		ModelAndView result;

		final Panel panel = this.panelService.findOne(panelId);
		panelComment.setPanel(panel);
		final Date date = new Date();
		panelComment.setMoment(date);

		this.validator.validate(panelComment, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("panelComment/create");
			result.addObject("panelComment", panelComment);
			result.addObject("panelId", panelId);
		} else
			try {
				final PanelComment saved = this.panelCommentService.save(panelComment);
				result = new ModelAndView("redirect:/panel/comment/show.do?commentId=" + saved.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("panelComment/create");
				result.addObject("panelComment", panelComment);
				result.addObject("message", "panelComment.commit.error");
				result.addObject("panelId", panelId);
			}
		return result;

	}

}
