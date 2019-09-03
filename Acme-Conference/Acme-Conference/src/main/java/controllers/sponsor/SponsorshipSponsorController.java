
package controllers.sponsor;

import java.util.Calendar;
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

import services.ConferenceService;
import services.ConfigurationParametersService;
import services.CreditCardService;
import services.SponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Conference;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorshipForm;

@Controller
@RequestMapping("/sponsorship/sponsor/")
public class SponsorshipSponsorController extends AbstractController {

	//Repository
	@Autowired
	private SponsorshipService				sponsorshipService;

	@Autowired
	private SponsorService					sponsorService;

	@Autowired
	private CreditCardService				creditCardService;

	@Autowired
	private ConferenceService				conferenceService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Sponsor sponsor = this.sponsorService.findByPrincipal();
			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findBySponsor(sponsor.getId());
			result = new ModelAndView("sponsorship/list");
			result.addObject("requestURI", "sponsorship/sponsor/list.do");
			result.addObject("sponsorships", sponsorships);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Sponsor sponsor = this.sponsorService.findByPrincipal();
			Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
			result = new ModelAndView("sponsorship/show");
			result.addObject("sponsorship", sponsorship);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final SponsorshipForm sponsorshipForm;
		sponsorshipForm = new SponsorshipForm();

		try {
			this.sponsorService.findByPrincipal();
			final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
			final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
			result = new ModelAndView("sponsorship/create");
			result.addObject("sponsorshipForm", sponsorshipForm);
			result.addObject("conferences", conferences);
			result.addObject("brandNames", brandNames);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SponsorshipForm sponsorshipForm, final BindingResult binding) {
		ModelAndView res;
		final Calendar now = Calendar.getInstance();
		Boolean b = false;
		final Integer y = now.get(Calendar.YEAR);
		final Integer m = now.get(Calendar.MONTH);
		if (binding.hasErrors()) {
			final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
			res = new ModelAndView("sponsorship/create");
			res.addObject("conferences", conferences);
			final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
			res.addObject("brandNames", brandNames);
			res.addObject("b", b);
		} else if (sponsorshipForm.getExpirationYear() <= y % 100 && sponsorshipForm.getExpirationMonth() < m) {
			final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
			res = new ModelAndView("sponsorship/create");
			res.addObject("conferences", conferences);
			final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
			res.addObject("brandNames", brandNames);
			b = true;
			res.addObject("b", b);
		} else
			try {
				Sponsorship sponsorship;
				this.sponsorService.findByPrincipal();
				sponsorship = this.sponsorshipService.recostruction(sponsorshipForm);
				this.sponsorshipService.save(sponsorship);
				res = new ModelAndView("redirect:/sponsorship/sponsor/list.do");

			} catch (final Throwable oops) {
				res = new ModelAndView("sponsorship/create");
				final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
				res.addObject("conferences", conferences);
				final Collection<String> brandNames = this.configurationParametersService.find().getCreditCardMakes();
				res.addObject("brandNames", brandNames);
				res.addObject("b", b);
				res.addObject("message", "sponsorship.commit.error");
			}

		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Sponsor sponsor = this.sponsorService.findByPrincipal();
			Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
			this.sponsorshipService.delete(sponsorship);

			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {

		ModelAndView result;

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Sponsor sponsor = this.sponsorService.findByPrincipal();
			Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
			final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
			result = new ModelAndView("sponsorship/edit");
			result.addObject("sponsorship", sponsorship);
			result.addObject("conferences", conferences);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView res;
		final Sponsorship sponsorshipFinal = this.sponsorshipService.recostructionEdit(sponsorship, binding);

		if (binding.hasErrors()) {
			final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
			res = new ModelAndView("sponsorship/edit");
			res.addObject("conferences", conferences);
		}

		else
			try {

				this.sponsorService.findByPrincipal();
				this.sponsorshipService.save(sponsorshipFinal);
				res = new ModelAndView("redirect:/sponsorship/sponsor/list.do");

			} catch (final Throwable oops) {
				final Collection<Conference> conferences = this.conferenceService.findConferencesFinal();
				res = new ModelAndView("sponsorship/edit");
				res.addObject("conferences", conferences);
				res.addObject("message", "sponsorship.commit.error");
			}
		return res;
	}
}
