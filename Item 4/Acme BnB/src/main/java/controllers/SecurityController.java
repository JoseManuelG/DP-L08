
package controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.InvoiceService;
import services.LessorService;
import services.TenantService;
import domain.Actor;
import domain.Auditor;
import domain.Lessor;
import domain.Tenant;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	TenantService			tenantService;
	@Autowired
	LessorService			lessorService;
	@Autowired
	ActorService			actorService;
	@Autowired
	AdministratorService	administratorService;
	@Autowired
	AuditorService			auditorService;
	@Autowired
	InvoiceService			invoiceService;


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
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else if (!((boolean) actorForm.getAcepted())) {

			result = createEditModelAndView(actorForm, "security.terms.error");
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result = new ModelAndView("security/edit");
		Actor actor = actorService.findByPrincipal();
		ActorForm actorForm = new ActorForm();
		actorForm.setName(actor.getName());
		actorForm.setSurname(actor.getSurname());
		actorForm.setEmail(actor.getEmail());
		actorForm.setPhone(actor.getPhone());
		actorForm.setPicture(actor.getPicture());

		actorForm.setUserAccount(actor.getUserAccount());
		result.addObject(actorForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Actor actor = actorService.findByPrincipal();
		Tenant tenant = null;
		Lessor lessor = null;
		Auditor auditor = null;
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(actor.getUserAccount().getAuthorities());
		String aux = authorities.get(0).getAuthority();

		if (aux.equals(Authority.AUDITOR)) {
			auditor = auditorService.findActorByPrincial();
			auditor = auditorService.reconstruct(actorForm, auditor, binding);

		} else if (aux.equals(Authority.LESSOR)) {
			lessor = lessorService.findByPrincipal();
			lessor = lessorService.reconstruct(actorForm, lessor, binding);

		} else if (aux.equals(Authority.TENANT)) {
			tenant = tenantService.findByPrincipal();
			tenant = tenantService.reconstruct(actorForm, tenant, binding);
		}

		if (binding.hasErrors()) {
			result = new ModelAndView("security/edit");
			result.addObject("actorForm", actorForm);
			result.addObject("message", null);
		} else {
			try {
				if (aux.equals("TENANT")) {

					tenantService.save(tenant);

				} else if (aux.equals("LESSOR")) {

					lessorService.save(lessor);

				} else if (aux.equals("AUDITOR")) {

					auditorService.save(auditor);

				}

			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "lessor.commit.error");
			}
			aux = aux.toLowerCase();
			result = new ModelAndView("redirect:../" + aux + "/myProfile.do");
		}

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete() {
		ModelAndView result;
		Actor actor = actorService.findByPrincipal();
		Tenant tenant = null;
		Lessor lessor = null;
		Auditor auditor = null;
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.addAll(actor.getUserAccount().getAuthorities());
		String aux = authorities.get(0).getAuthority();

		if (aux.equals(Authority.AUDITOR)) {
			auditor = auditorService.findActorByPrincial();

		} else if (aux.equals(Authority.LESSOR)) {
			lessor = lessorService.findByPrincipal();

		} else if (aux.equals(Authority.TENANT)) {
			tenant = tenantService.findByPrincipal();
		}

		try {

			if (aux.equals("TENANT")) {
				invoiceService.deleteAll(tenant);
				tenantService.delete(tenant);

			} else if (aux.equals("LESSOR")) {
				lessorService.delete(lessor);

			} else if (aux.equals("AUDITOR")) {
				auditorService.delete(auditor);

			}
			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (Exception e) {
			aux = aux.toLowerCase();
			result = new ModelAndView("redirect:../" + aux + "/myProfile.do");
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
		result = new ModelAndView("security/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}
}
