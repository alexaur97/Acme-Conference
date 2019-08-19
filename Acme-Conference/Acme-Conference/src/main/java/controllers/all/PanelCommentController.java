package controllers.all;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Validator;

import controllers.AbstractController;
import domain.Panel;
import domain.PanelComment;
import services.PanelCommentService;
import services.PanelService;

@Controller
@RequestMapping("/panel/comment")
public class PanelCommentController extends AbstractController {

	@Autowired
	private PanelCommentService panelCommentService;

	@Autowired
	private PanelService panelService;
	
	@Autowired
	private Validator validator;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int panelId) {
		ModelAndView result;
		try {
			PanelComment panelComment = this.panelCommentService.create();
			result = new ModelAndView("panelComment/create");
			result.addObject("panelComment", panelComment);
			result.addObject("panelId", panelId);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int commentId) {
		ModelAndView result;
		try {
			PanelComment panelComment = this.panelCommentService.findOne(commentId);
			result = new ModelAndView("panelComment/show");
			result.addObject("panelComment", panelComment);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam int panelId,@ModelAttribute("panelComment") PanelComment panelComment, BindingResult binding) {
		ModelAndView result;
		
		Panel panel = this.panelService.findOne(panelId);
		panelComment.setPanel(panel);
		Date date = new Date();
		panelComment.setMoment(date);
		
		this.validator.validate(panelComment, binding);
		
		if (binding.hasErrors()) {
			result = new ModelAndView("panelComment/create");
			result.addObject("panelComment", panelComment);
			result.addObject("panelId", panelId);
		} else
			try {
				PanelComment saved = this.panelCommentService.save(panelComment);
				result = new ModelAndView("redirect:/panel/comment/show.do?commentId=" + saved.getId());
			} catch (Throwable oops) {
				result = new ModelAndView("panelComment/create");
				result.addObject("panelComment", panelComment);
				result.addObject("message", "panelComment.commit.error");
				result.addObject("panelId", panelId);
			}
		return result;

	}

}
