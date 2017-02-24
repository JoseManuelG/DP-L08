
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.TenantService;
import domain.Tenant;

@Controller
@RequestMapping("/tenant")
public class TenantController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public TenantController() {
		super();
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("tenant/view");

		Tenant tenant = tenantService.findByPrincipal();

		result.addObject("tenant", tenant);
		result.addObject("comments", tenant.getComments());
		result.addObject("socialIdentities", tenant.getSocialIdentities());
		result.addObject("requestURI", "tenant/myProfile.do");
		result.addObject("esMiPerfil", true);
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(int tenantId) {
		ModelAndView result;
		result = new ModelAndView("tenant/view");

		Tenant tenant = tenantService.findOne(tenantId);

		result.addObject("tenant", tenant);
		result.addObject("comments", tenant.getComments());
		result.addObject("requestURI", "tenant/view.do");
		result.addObject("socialIdentities", tenant.getSocialIdentities());
		result.addObject("esMiPerfil", false);
		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Tenant tenant = (Tenant) customerService.findActorByPrincial();
		result = createEditModelAndView(tenant);
		return result;
	}

	// Save -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid Tenant tenant, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(tenant);
		} else {
			try {
				tenantService.save(tenant);
				result = new ModelAndView("../");

			} catch (Throwable oops) {
				result = createEditModelAndView(tenant, "tenant.commit.error");
			}
		}

		return result;
	}

	// Delete -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Tenant tenant, BindingResult binding) {
		ModelAndView result;

		try {
			tenantService.delete(tenant);
			result = new ModelAndView("../");
		} catch (Throwable oops) {
			result = createEditModelAndView(tenant, "tenant.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Tenant tenant) {
		ModelAndView result;

		result = createEditModelAndView(tenant, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Tenant tenant, String message) {
		ModelAndView result;
		result = new ModelAndView("tenant/edit");
		result.addObject("tenant", tenant);
		result.addObject("message", message);

		return result;
	}

}
