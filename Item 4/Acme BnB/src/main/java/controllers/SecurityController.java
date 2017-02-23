
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.LessorService;
import services.TenantService;
import domain.Actor;
import domain.Lessor;
import domain.Tenant;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	TenantService	tenantService;
	@Autowired
	LessorService	lessorService;
	@Autowired
	ActorService	actorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		ActorForm actorForm = new ActorForm();
		result = createEditModelAndView(actorForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Tenant tenant = null;
		Lessor lessor = null;
		if (actorForm.getTypeOfActor().equals("TENANT")) {
			tenant = tenantService.reconstruct(actorForm, binding);
		} else if (actorForm.getTypeOfActor().equals("LESSOR")) {
			lessor = lessorService.reconstruct(actorForm, binding);
		}
		if (binding.hasErrors() && !actorForm.getAcepted()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				if (actorForm.getTypeOfActor().equals("TENANT")) {

					tenantService.save(tenant);

				} else if (actorForm.getTypeOfActor().equals("LESSOR")) {

					lessorService.save(lessor);

				}

				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "lessor.commit.error");
			}
		}

		return result;
	}
	// Edit ---------------------------------------------------------------
	
		@RequestMapping(value="/edit", method=RequestMethod.GET)
		public ModelAndView edit(){
			ModelAndView result;
			Actor actor= actorService .findByPrincipal();
			ActorForm actorForm= new ActorForm();
			actorForm.setName(actor.getName());
			actorForm.setSurname(actor.getSurname());
			actorForm.setEmail(actor.getEmail());
			actorForm.setPhone(actor.getPhone());
			actorForm.setPicture(actor.getPicture());
			
			actorForm.setUserName(actor.getUserAccount().getUsername());
			actorForm.setPassword(actor.getUserAccount().getPassword());
			
			
			result = createEditModelAndView(actorForm);
			return result;
		}
		

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ActorForm actorForm) {
		ModelAndView result;

		result = createEditModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm actorForm, String message) {
		ModelAndView result;
		result = new ModelAndView("security/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}
}
