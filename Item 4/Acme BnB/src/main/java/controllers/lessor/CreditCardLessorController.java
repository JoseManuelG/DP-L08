
package controllers.lessor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.CustomerService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Lessor;

@Controller
@RequestMapping("/creditCard/lessor")
public class CreditCardLessorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public CreditCardLessorController() {
		super();
	}

	// Create --------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final CreditCard creditCard = this.creditCardService.create();
		final Lessor lessor = (Lessor) this.customerService.findActorByPrincial();
		lessor.setCreditCard(creditCard);
		result = this.createEditModelAndView(creditCard);
		return result;

	}
	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myCreditCard", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("creditCard/lessor/view");
		final Lessor lessor = (Lessor) this.customerService.findActorByPrincial();
		if (lessor.getCreditCard() != null) {
			final CreditCard creditCard = this.creditCardService.copy(lessor.getCreditCard());
			this.creditCardService.maskCreditCard(creditCard);
			result.addObject("creditCard", creditCard);
		} else
			result.addObject("editable", Boolean.FALSE);
		return result;
	}
	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Lessor lessor = (Lessor) this.customerService.findActorByPrincial();
		result = this.createEditModelAndView(lessor.getCreditCard());
		return result;
	}

	// Save ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(creditCard);
		} else
			try {
				this.creditCardService.saveForLessor(creditCard);
				result = new ModelAndView("redirect:../lessor/myCreditCard.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, oops.getMessage());
			}

		return result;
	}

	// Delete ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;

		Assert.notNull(creditCard);
		System.out.println(binding);
		try {
			this.creditCardService.delete(creditCard);
			result = new ModelAndView("redirect:../lessor/myCreditCard.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(creditCard, "creditCard.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String message) {
		ModelAndView result;
		result = new ModelAndView("creditCard/lessor/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);
		return result;
	}

}
