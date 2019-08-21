package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuthorService;
import services.ConferenceService;
import services.MessageService;
import services.ReviewerService;
import services.SponsorService;
import services.TopicService;

import controllers.AbstractController;
import domain.Actor;
import domain.Author;
import domain.Conference;
import domain.Message;
import domain.Sponsor;
import domain.Topic;
import forms.BroadcastConferenceForm;
import forms.BroadcastUsersForm;
import forms.RegisterSponsorAndAuthorForm;

@Controller
@RequestMapping("/message/administrator")
public class BroadcastAdministratorController extends AbstractController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ConferenceService conferenceService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping(value = "/broadcastAll", method = RequestMethod.GET)
	public ModelAndView broadcastAll() {
		ModelAndView res;
		try {
			final BroadcastUsersForm broadcastUsersForm = new BroadcastUsersForm();
			final Collection<Topic> topics = this.topicService.findAll();
			res = new ModelAndView("message/broadcastAll");
			res.addObject("broadcastUsersForm", broadcastUsersForm);
			res.addObject("topics", topics);
			res.addObject("action", "message/administrator/broadcastAll.do");
			res.addObject("submitAction", "broadcastAll");
			
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}

		return res;
	}

	@RequestMapping(value = "/broadcastAll", method = RequestMethod.POST, params = "broadcastAll")
	public ModelAndView saveBroadcastAll(@Valid final BroadcastUsersForm broadcastUsersForm,
		final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			final Collection<Topic> topics = this.topicService.findAll();
			res = new ModelAndView("message/broadcastAll");
			res.addObject("topics", topics);
			res.addObject("submitAction", "broadcastAll");
			res.addObject("broadcastUsersForm", broadcastUsersForm);
		} else
			try {
				res = new ModelAndView("message/broadcastAll");
				Collection<Actor> actors = this.actorService.findAll();
				for(Actor a: actors){
					Message m = this.messageService.recontructForBroadcast(broadcastUsersForm,a);
					this.messageService.save(m);
					Message copy = this.messageService.createMessageCopy(m);
					this.messageService.save(copy);
				}
				res = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("message/broadcastAll");
				res.addObject("broadcastUsersForm", broadcastUsersForm);
			}

		return res;
	}
	
	@RequestMapping(value = "/broadcastAuthor", method = RequestMethod.GET)
	public ModelAndView broadcastAuthor() {
		ModelAndView res;
		try {
			final BroadcastUsersForm broadcastUsersForm = new BroadcastUsersForm();
			final Collection<Topic> topics = this.topicService.findAll();
			res = new ModelAndView("message/broadcastAuthor");
			res.addObject("broadcastUsersForm", broadcastUsersForm);
			res.addObject("topics", topics);
			res.addObject("action", "message/administrator/broadcastAuthor.do");
			res.addObject("submitAction", "broadcastAuthor");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}

		return res;
	}

	@RequestMapping(value = "/broadcastAuthor", method = RequestMethod.POST, params = "broadcastAuthor")
	public ModelAndView saveBroadcastAuthor(@Valid final BroadcastUsersForm broadcastUsersForm,
		final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			final Collection<Topic> topics = this.topicService.findAll();
			res = new ModelAndView("message/broadcastAuthor");
			res.addObject("topics", topics);
			res.addObject("submitAction", "broadcastAuthor");
			res.addObject("broadcastUsersForm", broadcastUsersForm);
		} else
			try {
				res = new ModelAndView("message/broadcastAuthor");
				Collection<Author> authors = this.authorService.findAll();
				for(Author a: authors){
					Message m = this.messageService.recontructForBroadcast(broadcastUsersForm,a);
					this.messageService.save(m);
					Message copy = this.messageService.createMessageCopy(m);
					this.messageService.save(copy);
				}
				res = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("message/broadcastAuthor");
				res.addObject("broadcastUsersForm", broadcastUsersForm);
			}

		return res;
	}
	
	@RequestMapping(value = "/broadcastAttendee", method = RequestMethod.GET)
	public ModelAndView broadcastAttendee() {
		ModelAndView res;
		try {
			final BroadcastConferenceForm broadcastConferenceForm = new BroadcastConferenceForm();
			final Collection<Topic> topics = this.topicService.findAll();
			final Collection<Conference> conferences = this.conferenceService.findAll();
			res = new ModelAndView("message/broadcastAttendee");
			res.addObject("broadcastConferenceForm", broadcastConferenceForm);
			res.addObject("topics", topics);
			res.addObject("conferences", conferences);
			res.addObject("action", "message/administrator/broadcastAttendee.do");
			res.addObject("submitAction", "broadcastAttendee");
			
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}

		return res;
	}

	@RequestMapping(value = "/broadcastAttendee", method = RequestMethod.POST, params = "broadcastAttendee")
	public ModelAndView saveBroadcastAttendee(@Valid final BroadcastConferenceForm broadcastConferenceForm,
		final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			final Collection<Topic> topics = this.topicService.findAll();
			final Collection<Conference> conferences = this.conferenceService.findAll();
			res = new ModelAndView("message/broadcastAttendee");
			res.addObject("topics", topics);
			res.addObject("conferences", conferences);
			res.addObject("submitAction", "broadcastAttendee");
			res.addObject("broadcastConferenceForm", broadcastConferenceForm);
		} else
			try {
				res = new ModelAndView("message/broadcastAttendee");
				Collection<Author> authors = this.authorService.getAuthorsRegisterInConference(broadcastConferenceForm.getConference().getId());
				for(Author a: authors){
					Message m = this.messageService.recontructForBroadcastToConference(broadcastConferenceForm,a);
					this.messageService.save(m);
					Message copy = this.messageService.createMessageCopy(m);
					this.messageService.save(copy);
				}
				res = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("message/broadcastAttendee");
				res.addObject("broadcastConferenceForm", broadcastConferenceForm);
			}

		return res;
	}
	
	@RequestMapping(value = "/broadcastSubmitters", method = RequestMethod.GET)
	public ModelAndView broadcastSubmitters() {
		ModelAndView res;
		try {
			final BroadcastConferenceForm broadcastConferenceForm = new BroadcastConferenceForm();
			final Collection<Topic> topics = this.topicService.findAll();
			final Collection<Conference> conferences = this.conferenceService.findAll();
			res = new ModelAndView("message/broadcastSubmitters");
			res.addObject("broadcastConferenceForm", broadcastConferenceForm);
			res.addObject("topics", topics);
			res.addObject("conferences", conferences);
			res.addObject("action", "message/administrator/broadcastSubmitters.do");
			res.addObject("submitAction", "broadcastSubmitters");
			
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}

		return res;
	}

	@RequestMapping(value = "/broadcastSubmitters", method = RequestMethod.POST, params = "broadcastSubmitters")
	public ModelAndView saveBroadcastSubmitters(@Valid final BroadcastConferenceForm broadcastConferenceForm,
		final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			final Collection<Topic> topics = this.topicService.findAll();
			final Collection<Conference> conferences = this.conferenceService.findAll();
			res = new ModelAndView("message/broadcastSubmitters");
			res.addObject("topics", topics);
			res.addObject("conferences", conferences);
			res.addObject("submitAction", "broadcastSubmitters");
			res.addObject("broadcastConferenceForm", broadcastConferenceForm);
		} else
			try {
				res = new ModelAndView("message/broadcastSubmitters");
				Collection<Author> authors = this.authorService.getAuthorsSubmittedToConference(broadcastConferenceForm.getConference().getId());
				for(Author a: authors){
					Message m = this.messageService.recontructForBroadcastToConference(broadcastConferenceForm,a);
					this.messageService.save(m);
					Message copy = this.messageService.createMessageCopy(m);
					this.messageService.save(copy);
				}
				res = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("message/broadcastSubmitters");
				res.addObject("broadcastConferenceForm", broadcastConferenceForm);
			}

		return res;
	}
	
	

}
