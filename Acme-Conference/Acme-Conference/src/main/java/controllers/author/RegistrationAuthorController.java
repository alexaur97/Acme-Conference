
package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConferenceService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Conference;
import domain.Registration;
import forms.RegistrationForm;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	@Autowired
	private RegistrationService	registrationService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private AuthorService		authorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		try {
			this.authorService.findByPrincipal();
			final RegistrationForm registrationForm = new RegistrationForm();
			final Collection<Conference> conferencias = this.conferenceService.findAll();
			result = new ModelAndView("registration/create");
			result.addObject("registrationForm", registrationForm);
			result.addObject("conferencias", conferencias);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			final Collection<Conference> conferencias = this.conferenceService.findAll();
			result = new ModelAndView("registration/create");
			result.addObject("registrationForm", registrationForm);
			result.addObject("conferencias", conferencias);
		} else
			try {
				final Registration regis = this.registrationService.constructByForm(registrationForm);
				final Registration saved = this.registrationService.save(regis);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/#");

			}
		return result;
	}
}
