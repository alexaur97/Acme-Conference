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
import domain.Conference;
import domain.ConferenceComment;
import services.ConferenceCommentService;
import services.ConferenceService;

@Controller
@RequestMapping("/conference/comment")
public class ConferenceCommentController extends AbstractController {

	@Autowired
	private ConferenceCommentService conferenceCommentService;

	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
	private Validator validator;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int conferenceId) {
		ModelAndView result;
		try {
			ConferenceComment conferenceComment = this.conferenceCommentService.create();
			result = new ModelAndView("conferenceComment/create");
			result.addObject("conferenceComment", conferenceComment);
			result.addObject("conferenceId", conferenceId);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int commentId) {
		ModelAndView result;
		try {
			ConferenceComment conferenceComment = this.conferenceCommentService.findOne(commentId);
			result = new ModelAndView("conferenceComment/show");
			result.addObject("conferenceComment", conferenceComment);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam int conferenceId,@ModelAttribute("conferenceComment") ConferenceComment conferenceComment, BindingResult binding) {
		ModelAndView result;
		
		Conference conference = this.conferenceService.findOne(conferenceId);
		conferenceComment.setConference(conference);
		Date date = new Date();
		conferenceComment.setMoment(date);
		
		this.validator.validate(conferenceComment, binding);
		
		if (binding.hasErrors()) {
			result = new ModelAndView("conferenceComment/create");
			result.addObject("conferenceComment", conferenceComment);
			result.addObject("conferenceId", conferenceId);
		} else
			try {
				ConferenceComment saved = this.conferenceCommentService.save(conferenceComment);
				result = new ModelAndView("redirect:/conference/comment/show.do?commentId=" + saved.getId());
			} catch (Throwable oops) {
				result = new ModelAndView("conferenceComment/create");
				result.addObject("conferenceComment", conferenceComment);
				result.addObject("message", "conferenceComment.commit.error");
				result.addObject("conferenceId", conferenceId);
			}
		return result;

	}

}
