
package controllers.administrator;

import miscellaneous.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section/administrator")
public class SectionAdministratorController extends AbstractController {

	@Autowired
	private SectionService			sectionService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TutorialService			tutorialService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int tutorialId) {
		ModelAndView result;
		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.notNull(tutorial);
			this.administratorService.findByPrincipal();
			final Section section = this.sectionService.create();
			result = new ModelAndView("section/edit");
			result.addObject("section", section);
			result.addObject("tutorial", tutorial);
			result.addObject("tutorialId", tutorialId);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId) {

		ModelAndView result;

		try {
			this.administratorService.findByPrincipal();
			final Section section = this.sectionService.findOne(sectionId);
			Assert.notNull(section);
			result = new ModelAndView("section/edit");
			result.addObject("section", section);
			final int tutorialId = section.getTutorial().getId();
			result.addObject("tutorialId", tutorialId);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam final int tutorialId, final Section section, final BindingResult binding) {

		ModelAndView result;
		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.notNull(tutorial);
			this.administratorService.findByPrincipal();
			final Section sectionF = this.sectionService.reconstruct(tutorial, section, binding);
			final Boolean a = Utils.validateURL(sectionF.getPictures());
			if (binding.hasErrors()) {
				result = new ModelAndView("section/edit");
				result.addObject("section", section);
				result.addObject("tutorialId", tutorialId);
			} else
				try {
					Assert.isTrue(a);
					this.sectionService.save(sectionF);
					result = new ModelAndView("redirect:/tutorial/administrator/show.do?tutorialId=" + tutorialId);

				} catch (final Throwable oops) {
					if (a.equals(false)) {
						result = new ModelAndView("section/edit");
						result.addObject("section", section);
						result.addObject("message", "section.picture.error");
						result.addObject("tutorialId", tutorialId);
					} else
						result = new ModelAndView("section/edit");
					result.addObject("section", section);
					result.addObject("message", "section.picture.error");
					result.addObject("tutorialId", tutorialId);
					result.addObject("message", "section.commit.error");

				}
		} catch (final Throwable oops) {
			result = new ModelAndView("section/edit");
			result.addObject("section", section);
			result.addObject("tutorialId", tutorialId);
			result.addObject("message", "section.commit.error");
		}
		return result;

	}
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Section section, final BindingResult binding) {
		ModelAndView result;
		final Section res = this.sectionService.findOne(section.getId());

		try {
			this.administratorService.findByPrincipal();
			final int tutorialId = res.getTutorial().getId();
			this.sectionService.delete(res);
			result = new ModelAndView("redirect:/tutorial/administrator/show.do?tutorialId=" + tutorialId);
		} catch (final Throwable oops) {
			result = new ModelAndView("section/edit");
			result.addObject("section", section);
			result.addObject("message", oops.getMessage());
		}
		return result;
	}

}
