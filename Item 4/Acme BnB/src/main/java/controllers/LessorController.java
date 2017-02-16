package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.CustomerService;
import services.LessorService;
import domain.Lessor;
import domain.SocialIdentity;
import forms.ActorForm;

@Controller
@RequestMapping("/lessor")
public class LessorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private CustomerService customerService;


	// Constructors -----------------------------------------------------------

	public LessorController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Lessor> lessors;
		lessors = lessorService.findAll();
		result = new ModelAndView("lessor/list");
		result.addObject("requestURI", "lessor/list.do");
		result.addObject("lessors", lessors);

		return result;
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) Integer lessorId) {
		ModelAndView result;
		result = new ModelAndView("lessor/view");
		Lessor lessor  = (Lessor) customerService.findActorByPrincial();
		Collection<SocialIdentity> socialIdentities = lessor.getSocialIdentities();
		if (socialIdentities != null) {
			result.addObject("socialIdentities", socialIdentities);
		} else {
			socialIdentities = new ArrayList<SocialIdentity>();
			result.addObject("socialIdentities", socialIdentities);
		}
		result.addObject("lessor", lessor);
		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = new ModelAndView("lessor/edit");
		Lessor lessor  = (Lessor) customerService.findActorByPrincial();
		ActorForm actorForm = new ActorForm();
		
		actorForm.setTypeOfActor("LESSOR");
		actorForm.setLessor(lessor);
		actorForm.getLessor().getUserAccount().setUsername(lessor.getUserAccount().getUsername());
		actorForm.getLessor().getUserAccount().setPassword(lessor.getUserAccount().getUsername());
		actorForm.getLessor().setName(lessor.getName());
		actorForm.getLessor().setSurname(lessor.getSurname());
		actorForm.getLessor().setEmail(lessor.getEmail());
		actorForm.getLessor().setPhone(lessor.getPhone());
		actorForm.getLessor().setPicture(lessor.getPicture());
		actorForm.setConditionsAccepted(false);
		
		result.addObject("actorForm", actorForm);
		result.addObject("typeActor", "LESSOR");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				Lessor lessor  = (Lessor) customerService.findActorByPrincial();

				UserAccount userAccount = lessor.getUserAccount();

				userAccount.setPassword(encoder.encodePassword( actorForm.getLessor().getUserAccount().getPassword(), null));
				userAccount.setUsername(actorForm.getLessor().getUserAccount().getUsername());

				lessor.setName(actorForm.getLessor().getName());
				lessor.setSurname(actorForm.getLessor().getSurname());
				lessor.setEmail(actorForm.getLessor().getEmail());
				lessor.setPhone(actorForm.getLessor().getPhone());
				lessor.setPicture(actorForm.getLessor().getPicture());
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				lessor.setUserAccount(userAccount);

				lessorService.save(lessor);

				result = this.view(null);
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "lessor.commit.error");
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;

		try {			
			lessorService.delete(actorForm.getLessor());
			result = new ModelAndView("redirect:myRecipes.do");
			//result.addObject("requestURI","recipe/user/delete.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(actorForm, "lessor.commit.error");
		}

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
		result = new ModelAndView("lessor/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}