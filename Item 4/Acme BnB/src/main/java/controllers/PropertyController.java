package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;
import domain.Property;

@Controller
@RequestMapping("/property")
public class PropertyController extends AbstractController {
	
	// Services -------------------------------------------------------------
	
	@Autowired
	PropertyService propertyService ;
	
	// Constructors -----------------------------------------------------------
	
	public PropertyController(){
		super();
	}
	
	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> properties;
		properties = propertyService.findAll();
		result = new ModelAndView("property/list");
		result.addObject("requestURI", "property/list.do");
		result.addObject("properties", properties);
		
		return result;
	}
	
	// View ---------------------------------------------------------------
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) Integer propertyId) {
		ModelAndView result;
		result = new ModelAndView("property/view");
		Property property  = propertyService.findOne(propertyId);
		result.addObject("property", property);
		return result;
	}
	
}
