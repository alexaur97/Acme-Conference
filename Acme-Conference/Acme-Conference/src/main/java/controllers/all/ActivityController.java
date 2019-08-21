package controllers.all;

import java.util.Collection;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import domain.Panel;
import domain.PanelComment;
import domain.Presentation;
import domain.PresentationComment;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;
import domain.TutorialComment;
import services.ConferenceCommentService;
import services.ConferenceService;
import services.PanelCommentService;
import services.PanelService;
import services.PresentationCommentService;
import services.PresentationService;
import services.TutorialCommentService;
import services.TutorialService;

@Controller
@RequestMapping("/conference/activity")
public class ActivityController extends AbstractController {
	
	@Autowired
	private PresentationService presentationService;
	
	@Autowired
	private TutorialService tutorialService;	
	
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private PresentationCommentService presentationCommentService;
	
	@Autowired
	private TutorialCommentService tutorialCommentService;	
	
	@Autowired
	private PanelCommentService panelCommentService;
	
	@RequestMapping(value = "/listByConference", method = RequestMethod.GET)
	public ModelAndView listByConference(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			final Collection<Presentation> presentations = this.presentationService.findByConference(conferenceId);
			final Collection<Tutorial> tutorials = this.tutorialService.findByConference(conferenceId);
			final Collection<Panel> panels = this.panelService.findByConference(conferenceId);
			result = new ModelAndView("activity/list");
			result.addObject("requestURI", "conferenceComment/listByConference.do");
			result.addObject("presentations", presentations);
			result.addObject("tutorials", tutorials);
			result.addObject("panels", panels);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	
	@RequestMapping(value = "/listCommentPresentation", method = RequestMethod.GET)
	public ModelAndView listCommentByPresentation(@RequestParam final int presentationId) {
		ModelAndView result;
		try {
			final Collection<PresentationComment> comments = this.presentationCommentService.listCommentByPresentation(presentationId);
			result = new ModelAndView("presentationComment/list");
			result.addObject("requestURI", "presentationComment/listCommentPresentation.do");
			result.addObject("comments", comments);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	
	@RequestMapping(value = "/listCommentTutorial", method = RequestMethod.GET)
	public ModelAndView listCommentTutorial(@RequestParam final int tutorialId) {
		ModelAndView result;
		try {
			final Collection<TutorialComment> comments = this.tutorialCommentService.listCommentByTutorial(tutorialId);
			result = new ModelAndView("tutorialComment/list");
			result.addObject("requestURI", "tutorialComment/listCommentTutorial.do");
			result.addObject("comments", comments);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	
	@RequestMapping(value = "/listCommentPanel", method = RequestMethod.GET)
	public ModelAndView listCommentPanel(@RequestParam final int panelId) {
		ModelAndView result;
		try {
			final Collection<PanelComment> comments = this.panelCommentService.listCommentByPanel(panelId);
			result = new ModelAndView("panelComment/list");
			result.addObject("requestURI", "panelComment/listCommentPanel.do");
			result.addObject("comments", comments);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
