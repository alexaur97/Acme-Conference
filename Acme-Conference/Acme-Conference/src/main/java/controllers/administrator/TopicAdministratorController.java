
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.TopicService;
import controllers.AbstractController;
import domain.Topic;

@Controller
@RequestMapping("/topic/administrator")
public class TopicAdministratorController extends AbstractController {

	@Autowired
	private TopicService			topicService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {

			this.administratorService.findByPrincipal();
			final Collection<Topic> topics = this.topicService.findAll();

			result = new ModelAndView("topic/list");
			result.addObject("requestURI", "topic/list.do");
			result.addObject("topics", topics);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			this.administratorService.findByPrincipal();
			final Topic topic = this.topicService.create();
			result = new ModelAndView("topic/edit");
			result.addObject("topic", topic);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int topicId) {

		ModelAndView result;

		try {
			this.administratorService.findByPrincipal();
			final Topic topic = this.topicService.findOne(topicId);
			result = new ModelAndView("topic/edit");
			result.addObject("topic", topic);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(final Topic topic, final BindingResult binding) {

		ModelAndView result;
		this.administratorService.findByPrincipal();
		final Topic topicF = this.topicService.reconstruct(topic, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("topic/edit");
			result.addObject("topic", topic);

		} else
			try {
				//Nos aseguramos que la fecha tiene que estar dentro de la fecha de la conferencia
				this.topicService.save(topicF);
				result = new ModelAndView("redirect:/topic/administrator/list.do");

			} catch (final Throwable oops) {

				result = new ModelAndView("redirect:/#");
			}
		return result;

	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Topic topic, final BindingResult binding) {
		ModelAndView result;
		final Topic res = this.topicService.findOne(topic.getId());

		try {
			this.administratorService.findByPrincipal();
			this.topicService.delete(res);
			result = new ModelAndView("redirect:/topic/administrator/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("topic/edit");
			result.addObject("topic", topic);
			result.addObject("message", oops.getMessage());
		}
		return result;
	}

}
