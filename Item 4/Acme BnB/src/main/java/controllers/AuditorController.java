/*
 * AdministratorController.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.AuditorService;
import domain.Audit;
import domain.Auditor;
import forms.ActorForm;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public AuditorController() {
		super();
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		Collection<Audit> audits;
		result = new ModelAndView("auditor/view");
		Auditor auditor;

		auditor = (Auditor) actorService.findByPrincipal();
		audits = auditor.getAudits();

		result.addObject("audits", audits);
		result.addObject(auditor);
		return result;
	}

	//registerAuditor--------------------------------------------------------------
	@RequestMapping(value = "/administrator/registerAuditor.do", method = RequestMethod.GET)
	public ModelAndView registerAuditor() {
		ModelAndView result;

		ActorForm actorForm = new ActorForm();
		result = new ModelAndView("auditor/edit");
		actorForm.setTypeOfActor("AUDITOR");
		result.addObject("actorForm", actorForm);

		result.addObject("requestURI", "auditor/administrator/registerAuditor.do");
		result.addObject("typeActor", "auditor");
		return result;
	}

	@RequestMapping(value = "/administrator/registerAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView registerAuditor(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		actorForm.setTypeOfActor("AUDITOR");

		if (binding.hasErrors()) {
			result = registerAuditorEditModelAndView(actorForm);
		} else {
			try {
				Auditor auditor = auditorService.create();

				UserAccount userAccount = new UserAccount();

				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getName());

				auditor.setName(actorForm.getName());
				auditor.setSurname(actorForm.getSurname());
				auditor.setEmail(actorForm.getEmail());
				auditor.setPhone(actorForm.getPhone());

				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				auditor.setUserAccount(userAccount);

				auditorService.save(auditor);

				result = new ModelAndView("auditor/view.do");

			} catch (Throwable oops) {
				result = registerAuditorEditModelAndView(actorForm, "recipe.commit.error");
				result.addObject("requestURI", "auditor/administrator/registerAuditor.do");

			}

		}
		return result;

	}
	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Auditor auditor = (Auditor) actorService.findByPrincipal();
		ActorForm actorForm = new ActorForm();
		result = new ModelAndView("auditor/edit");
		actorForm.setEmail(auditor.getEmail());
		actorForm.setName(auditor.getName());
		actorForm.setPassword(auditor.getUserAccount().getPassword());
		actorForm.setPhone(auditor.getPhone());
		actorForm.setSurname(auditor.getSurname());
		actorForm.setTypeOfActor("AUDITOR");
		actorForm.setName(auditor.getUserAccount().getUsername());
		result.addObject("actorForm", actorForm);
		result.addObject("requestURI", "auditor/edit.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		actorForm.setTypeOfActor("AUDITOR");
		if (binding.hasErrors()) {
			System.out.print(binding.getAllErrors().toString());
			result = registerAuditorEditModelAndView(actorForm);
		} else {
			try {
				Auditor auditor = (Auditor) actorService.findByPrincipal();

				UserAccount userAccount = auditor.getUserAccount();

				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));
				userAccount.setUsername(actorForm.getName());

				auditor.setName(actorForm.getName());
				auditor.setSurname(actorForm.getSurname());
				auditor.setEmail(actorForm.getEmail());
				auditor.setPhone(actorForm.getPhone());
				Collection<Authority> authorities = new ArrayList<Authority>();
				Authority authority = new Authority();
				authority.setAuthority(actorForm.getTypeOfActor());
				authorities.add(authority);
				userAccount.setAuthorities(authorities);
				auditor.setUserAccount(userAccount);

				auditorService.save(auditor);

				result = new ModelAndView("auditor/view.do");
			} catch (Throwable oops) {
				result = registerAuditorEditModelAndView(actorForm, "recipe.commit.error");
				result.addObject("requestURI", "auditor/edit.do");

			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView registerAuditorEditModelAndView(ActorForm actorForm) {
		ModelAndView result;

		result = registerAuditorEditModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView registerAuditorEditModelAndView(ActorForm actorForm, String message) {
		ModelAndView result;
		result = new ModelAndView("user/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}
