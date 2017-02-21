
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
import services.LessorService;
import services.TenantService;
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


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		ActorForm actorForm = new ActorForm();
		result = createEditModelAndView(actorForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				UserAccount userAccount = new UserAccount();
				userAccount.setPassword(encoder.encodePassword(actorForm.getPassword(), null));

				if (actorForm.getTypeOfActor().equals("TENANT")) {
					Tenant tenant = tenantService.create();

					tenant.setName(actorForm.getName());
					tenant.setSurname(actorForm.getSurname());
					tenant.setPicture(actorForm.getPicture());
					tenant.setEmail(actorForm.getEmail());
					tenant.setPhone(actorForm.getPhone());

					Collection<Authority> authorities = new ArrayList<Authority>();
					Authority authority = new Authority();
					authority.setAuthority(actorForm.getTypeOfActor());
					authorities.add(authority);
					userAccount.setAuthorities(authorities);
					tenant.setUserAccount(userAccount);
					tenantService.save(tenant);

				} else if (actorForm.getTypeOfActor().equals("LESSOR")) {
					Lessor lessor = lessorService.create();

					lessor.setName(actorForm.getName());
					lessor.setSurname(actorForm.getSurname());
					lessor.setPicture(actorForm.getPicture());
					lessor.setEmail(actorForm.getEmail());
					lessor.setPhone(actorForm.getPhone());

					Collection<Authority> authorities = new ArrayList<Authority>();
					Authority authority = new Authority();
					authority.setAuthority(actorForm.getTypeOfActor());
					authorities.add(authority);
					userAccount.setAuthorities(authorities);
					lessor.setUserAccount(userAccount);
					lessorService.save(lessor);

				}

				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "recipe.commit.error");
			}
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
