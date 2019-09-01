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
import domain.Section;
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
import services.SectionService;
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
	
	@Autowired
	private SectionService sectionService;
	
	@RequestMapping(value = "/listByConference", method = RequestMethod.GET)
	public ModelAndView listByConference(@RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			final Collection<Presentation> presentations = this.presentationService.findByConference(conferenceId);
			final Collection<Tutorial> tutorials = this.tutorialService.findByConference(conferenceId);
			final Collection<Panel> panels = this.panelService.findByConference(conferenceId);
			result = new ModelAndView("activity/list");
			result.addObject("requestURI", "conference/activity/listByConference.do");
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
			result.addObject("requestURI", "conference/activity/listCommentPresentation.do");
			result.addObject("comments", comments);
			result.addObject("presentationId",presentationId);
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
			result.addObject("requestURI", "conference/activity/listCommentTutorial.do");
			result.addObject("comments", comments);
			result.addObject("tutorialId", tutorialId);
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
			result.addObject("requestURI", "conference/activity/listCommentPanel.do");
			result.addObject("comments", comments);
			result.addObject("panelId", panelId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	
	@RequestMapping(value = "/panel/show", method = RequestMethod.GET)
	public ModelAndView showPanel(@RequestParam final int panelId) {
		ModelAndView result;
		try {
			Panel panel = this.panelService.findOne(panelId);
			result = new ModelAndView("panel/show");
			result.addObject("panel", panel);
			final Integer duracionSegundos = (int) ((panel.getendMoment().getTime() - panel.getStartMoment().getTime()) / 1000);
			final Integer endMoment = duracionSegundos / 60;
			result.addObject("endMoment", endMoment);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/presentation/show", method = RequestMethod.GET)
	public ModelAndView showPresentation(@RequestParam final int presentationId) {
		ModelAndView result;
		try {
			Presentation presentation = this.presentationService.findOne(presentationId);
			result = new ModelAndView("presentation/show");
			result.addObject("presentation", presentation);
			final Integer duracionSegundos = (int) ((presentation.getendMoment().getTime() - presentation.getStartMoment().getTime()) / 1000);
			final Integer endMoment = duracionSegundos / 60;
			result.addObject("endMoment", endMoment);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/tutorial/show", method = RequestMethod.GET)
	public ModelAndView showTutorial(@RequestParam final int tutorialId) {
		ModelAndView result;
		try {
			final Collection<Section> sections = this.sectionService.findByTutorial(tutorialId);
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			result = new ModelAndView("tutorial/show");
			result.addObject("tutorial", tutorial);
			result.addObject("sections", sections);
			final Integer duracionSegundos = (int) ((tutorial.getendMoment().getTime() - tutorial.getStartMoment().getTime()) / 1000);
			final Integer endMoment = duracionSegundos / 60;
			result.addObject("endMoment", endMoment);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	
}
