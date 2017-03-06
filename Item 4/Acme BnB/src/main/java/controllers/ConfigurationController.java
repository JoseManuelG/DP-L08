
package controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Configuration;
import domain.SocialIdentity;

import security.Authority;
import services.ActorService;
import services.ConfigurationService;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	ActorService	actorService;

	

	// Constructors -----------------------------------------------------------

	public ConfigurationController() {
		super();
	}

	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView save() {
		Configuration configuration = configurationService.findOne();
		ModelAndView result= this.createEditModelAndView(configuration);	
		return result;
	
	
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save( @Valid Configuration configuration, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(configuration);
			System.out.println(binding.getAllErrors().toString());
		} else {
			try {
				configurationService.save(configuration);
				result = new ModelAndView("redirect:../../");
				
		} catch (Throwable oops) {
			result = createEditModelAndView(configuration, "configuration.commit.error");	
			}
			
	}
			return result;
	}
	
	protected ModelAndView createEditModelAndView(Configuration configuration) {
	ModelAndView result;

	result = createEditModelAndView(configuration, null);
	
	return result;
}	

	protected ModelAndView createEditModelAndView(Configuration configuration, String message) {
		ModelAndView result;
		result = new ModelAndView("configuration/administrator/edit");
		
		result.addObject("configuration", configuration);
		result.addObject("message", message);
		result.addObject("requestURI","configuration/administrator/edit.do");
		return result;
	}

}
