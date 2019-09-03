
package controllers.all;

import java.util.Collection;
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

import services.ConferenceCommentService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;
import domain.ConferenceComment;

@Controller
@RequestMapping("/conference/comment")
public class ConferenceCommentController extends AbstractController {

	@Autowired
	private ConferenceCommentService	conferenceCommentService;

	@Autowired
	private ConferenceService			conferenceService;

	@Autowired
	private Validator					validator;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			final Conference conference = this.conferenceService.findOne(conferenceId);
			Assert.notNull(conference);
			final ConferenceComment conferenceComment = this.conferenceCommentService.create();
			result = new ModelAndView("conferenceComment/create");
			result.addObject("conferenceComment", conferenceComment);
			result.addObject("conferenceId", conferenceId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int commentId) {
		ModelAndView result;
		try {
			final ConferenceComment conferenceComment = this.conferenceCommentService.findOne(commentId);
			result = new ModelAndView("conferenceComment/show");
			result.addObject("conferenceComment", conferenceComment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@RequestParam final int conferenceId, @ModelAttribute("conferenceComment") final ConferenceComment conferenceComment, final BindingResult binding) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);
		conferenceComment.setConference(conference);
		final Date date = new Date();
		conferenceComment.setMoment(date);

		this.validator.validate(conferenceComment, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("conferenceComment/create");
			result.addObject("conferenceComment", conferenceComment);
			result.addObject("conferenceId", conferenceId);
		} else
			try {
				final ConferenceComment saved = this.conferenceCommentService.save(conferenceComment);
				result = new ModelAndView("redirect:/conference/comment/show.do?commentId=" + saved.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("conferenceComment/create");
				result.addObject("conferenceComment", conferenceComment);
				result.addObject("message", "conferenceComment.commit.error");
				result.addObject("conferenceId", conferenceId);
			}
		return result;

	}

	@RequestMapping(value = "/listByConference", method = RequestMethod.GET)
	public ModelAndView listByConference(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			final Collection<ConferenceComment> comments = this.conferenceCommentService.listCommentsByConference(conferenceId);
			result = new ModelAndView("conferenceComment/list");
			result.addObject("requestURI", "conference/comment/listByConference.do");
			result.addObject("comments", comments);
			result.addObject("conferenceId", conferenceId);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
