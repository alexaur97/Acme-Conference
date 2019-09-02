
package controllers.author;

import java.util.Collection;

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
import services.ConferenceService;
import services.ConfigurationParametersService;
import services.CreditCardService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Registration;
import forms.RegistrationForm;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	@Autowired
	private RegistrationService				registrationService;

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private ConferenceService				conferenceService;

	@Autowired
	private CreditCardService				creditCardService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {

			this.authorService.findByPrincipal();
			final int id = this.authorService.findByPrincipal().getId();
			final Collection<Registration> registrations = this.registrationService.findRegistrationByAuthor(id);

			result = new ModelAndView("registration/list");
			result.addObject("requestURI", "registration/author/list.do");
			result.addObject("registrations", registrations);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int registrationId) {
		ModelAndView result;
		try {
			final Author author = this.authorService.findByPrincipal();
			final Registration registration = this.registrationService.findOne(registrationId);
			Assert.notNull(registration);
			Assert.isTrue(registration.getAuthor().equals(author));
			result = new ModelAndView("registration/show");
			result.addObject("requestURI", "registration/author/show.do");
			result.addObject("registration", registration);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			this.authorService.findByPrincipal();
			final RegistrationForm registrationForm = new RegistrationForm();
			final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
			final Collection<Conference> conferencias = this.conferenceService.findNextConferences();
			result = new ModelAndView("registration/create");
			result.addObject("registrationForm", registrationForm);
			result.addObject("conferencias", conferencias);
			result.addObject("brandNames", brandNames);

			//result.addObject("brands", brands);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
			final Collection<Conference> conferencias = this.conferenceService.findNextConferences();
			result = new ModelAndView("registration/create");
			result.addObject("registrationForm", registrationForm);
			result.addObject("conferencias", conferencias);
			result.addObject("brandNames", brandNames);
		} else
			try {
				final Author author = this.authorService.findByPrincipal();
				final Registration regis = this.registrationService.constructByForm(registrationForm);
				Assert.isTrue(regis.getConference().getMode().equals("FINAL"));
				Assert.isTrue(regis.getAuthor().equals(author));
				this.registrationService.save(regis);
				result = new ModelAndView("redirect:/registration/author/list.do");
			} catch (final Throwable oops) {
				final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
				final Collection<Conference> conferencias = this.conferenceService.findNextConferences();
				result = new ModelAndView("registration/create");
				result.addObject("registrationForm", registrationForm);
				result.addObject("conferencias", conferencias);
				result.addObject("brandNames", brandNames);
				result.addObject("message", "registration.creditCard.error");

			}
		return result;
	}
}
